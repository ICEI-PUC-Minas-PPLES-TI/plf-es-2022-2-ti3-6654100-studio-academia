package com.example.empresasjava.service.impl;

import com.example.empresasjava.enums.RolesEnum;
import com.example.empresasjava.models.*;
import com.example.empresasjava.models.RequestEntity.UserRequest;
import com.example.empresasjava.models.dto.AddressDto;
import com.example.empresasjava.models.dto.UserDto;
import com.example.empresasjava.repository.AddressRepository;
import com.example.empresasjava.repository.UserRepository;
import com.example.empresasjava.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StateService stateService;

    @Override
    public UserDto create(UserRequest user) throws NonUniqueResultException, NotFoundException {

        Optional<User> usr = Optional.ofNullable(this.userRepository.findOneByEmail(user.getEmail()));

        if(!usr.isPresent()){
            List<Role> roles = Optional.of(this.roleService.findAllByNameIn(user.getRoles()))
                    .orElseThrow(() -> new NoSuchElementException("Role not Founded"));

            user.setPassword(this.bcryptEncoder.encode(user.getPassword()));


            Cities city = this.cityService.findByCity(user.getAddress().getCity());
            States state = this.stateService.findByUf(user.getAddress().getState());

            AddressDto addressDto = user.getAddress();
            Address address = this.addressRepository.save(Address.fromAddressDTO(addressDto, city, state));

            return UserDto.fromUser(this.userRepository.save(UserRequest.toUser(user, roles, address)));
        }else{
            throw new NonUniqueResultException("Email ja cadastrado!");
        }

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(this.userRepository.findOneByEmail(email));
    }

    @Override
    public UserDto save(User user) {
        user.setPassword(this.bcryptEncoder.encode(user.getPassword()));
        return UserDto.fromUser(this.userRepository.save(user));
    }

    @Override
    public UserDto editUser(UserRequest userRequest) {
        User user = this.userRepository.findOneByEmail(userRequest.getEmail());

        //usuarios nao administradores nao podem editar Roles
        //usuarios nao administradores nao podem adicionar Roles
        User userByPrincipal = this.getUserByPrincipal();

        if(this.hasRole(userByPrincipal, RolesEnum.ADMIN)) {
            List<Role> NewRoles = this.roleService.findAllByNameIn(userRequest.getRoles());
            user.setRoles(NewRoles);
            user.setName(userRequest.getName());
            user.setPassword(this.bcryptEncoder.encode(user.getPassword()));
        }

        //Usuarios so podem editar eles mesmos
        if(user == userByPrincipal) {
            user.setName(userRequest.getName());
            user.setPassword(this.bcryptEncoder.encode(user.getPassword()));
        }else {
            throw new BadCredentialsException("Usuários não administradores não podem alterar outros usuários!");
        }

        return UserDto.fromUser(this.userRepository.save(user));
    }

    @Override
    public boolean hasRole(User user, RolesEnum checkRole) {

        return user.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals(checkRole.getCode()));
    }

    @Override
    public UserDto deleteUser(String email) {
        User user = this.userRepository.findOneByEmail(email);

        user.setDeletedAt(new Date());
        return UserDto.fromUser(this.userRepository.save(user));
    }

    @Override
    public UserDto deleteLoggedUser() {
        User loggedUser = this.getUserByPrincipal();
        loggedUser.setDeletedAt(new Date());
        return UserDto.fromUser(loggedUser);
    }


    @Override
    public User getUserByPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findOneByEmail(((UserDetails)principal).getUsername());

    }


    @Override
    public Page<User> listUsersByPage(Pageable page) {
        return this.userRepository.findAllByDeletedAtIsNullOrderByName(page);
    }

}