import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tickets } from '../models/tickets';
import {tick} from "@angular/core/testing";
import {Project} from "../models/project";

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private REST_API_SERVER = 'http://localhost:8080/api/projects';
  constructor(private httpClient: HttpClient) {}

  public getAllProjects(): Observable<Array<Project>> {
    return this.httpClient.get<Array<Project>>(this.REST_API_SERVER, {});
  }

  public showProject(projectId: String): Observable<Project> {
    return this.httpClient.get<Project>(this.REST_API_SERVER + '/' + projectId, {});
  }

  public addProject(project: Project): Observable<Project> {
    return this.httpClient.post<Project>(
      this.REST_API_SERVER + '/add',
      project,
      {}
    );
  }

  public updateProject(project: Project): Observable<Project> {
    return this.httpClient.post<Project>(
      this.REST_API_SERVER + '/' + project.id + '/edit',
      project,
      {}
    );
  }

  public deleteProject(projectId: string): Observable<Project> {
    return this.httpClient.post<Project>(
      this.REST_API_SERVER + '/' + projectId + '/delete',
      {}
    );
  }
}
