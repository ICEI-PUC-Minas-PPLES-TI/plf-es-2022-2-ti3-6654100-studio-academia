package com.example.empresasjava.service;

import com.example.empresasjava.models.PhysicalAssessment;
import com.example.empresasjava.models.Plans;
import com.example.empresasjava.models.RequestEntity.PhysicalAssessmentRequest;
import com.example.empresasjava.models.RequestEntity.PlansRequest;
import com.example.empresasjava.models.ResponseEntity.PhysicalAssessmentResponse;
import com.example.empresasjava.models.ResponseEntity.PlansResponse;
import com.itextpdf.text.DocumentException;
import javassist.NotFoundException;
import org.flywaydb.core.internal.resource.classpath.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NonUniqueResultException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface PhysicalAssessmentService {

    PhysicalAssessmentResponse createPhysicalAssessment (PhysicalAssessmentRequest physicalAssessmentRequest) throws NonUniqueResultException, NotFoundException;

    PhysicalAssessmentResponse editPhysicalAssessment(PhysicalAssessmentRequest physicalAssessmentRequest)throws  NotFoundException;

    PhysicalAssessmentResponse deletePhysicalAssessment(Long physicalAssessmentId)throws  NotFoundException;

    Page<PhysicalAssessment> listPhysicalAssessmentsByPage(Pageable pages)throws  NotFoundException;

    Page<PhysicalAssessment> listSpecificUserPhysicalAssessmentsByPage(Pageable pages, Long idUser)throws  NotFoundException;

    Page<PhysicalAssessment> listSpecificProfessionalPhysicalAssessmentsByPage(Pageable pages, Long professionalId)throws  NotFoundException;

    PhysicalAssessmentResponse getPhysicalAssessmentById(Long physicalAssessmentId)throws  NotFoundException;

    List<PhysicalAssessment> getSpecificUserPhysicalAssessments(Long idUser)throws  NotFoundException;

    List<PhysicalAssessment> getSpecificProfessionalPhysicalAssessments(Long professionalId)throws  NotFoundException;
    String uploadPdf(MultipartFile pdfPhysicalAssessment)throws NonUniqueResultException, NotFoundException, IOException;
    byte[] getPdf(Long physicalAssessmentId) throws NonUniqueResultException, NotFoundException, IOException;

    ByteArrayInputStream getPdf2(Long physicalAssessmentId) throws NonUniqueResultException, NotFoundException, IOException, DocumentException;
}
