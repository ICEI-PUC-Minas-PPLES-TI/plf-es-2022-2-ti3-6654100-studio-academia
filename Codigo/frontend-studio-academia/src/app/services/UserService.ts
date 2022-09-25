import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: "root",
})
export class  UserService {

  private static API_URLS = {
    CREATE: `${environment.apiUrl}/user/create`,
  };

  constructor(private http: HttpClient, private router: Router) {}

  private sessionSubject = new BehaviorSubject<any>(
    this.getSession()
  );

  create(body: any): Observable<any> {
    return this.http.post(UserService.API_URLS.CREATE, body);
  }

  listByPage(pageRequest: number, sizeRequest: number, keySearch: string){
    let searchPageUrl = `${environment.apiUrl}/user/page/${pageRequest}/size/${sizeRequest}`

    if(keySearch.length > 0){
      searchPageUrl +='/name/'+keySearch
    }

    return this.http.get(searchPageUrl)
  }

  getSession() {
    return JSON.parse(localStorage.getItem("session") || "{}");
  }
}
