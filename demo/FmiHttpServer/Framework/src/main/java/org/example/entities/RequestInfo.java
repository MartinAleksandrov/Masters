package org.example.entities;

import java.util.Objects;

public class RequestInfo{

    private  String httpMethod;
    private  String httpEndPoint;

    public  RequestInfo(String httpMethod, String httpEndPoint){

        this.httpMethod = httpMethod;
        this.httpEndPoint = httpEndPoint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, httpEndPoint);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        RequestInfo info = (RequestInfo) obj;

        return  Objects.equals(httpEndPoint , httpEndPoint)
                && Objects.equals(httpMethod , info.httpMethod);
    }
}
