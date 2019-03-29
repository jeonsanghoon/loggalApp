package com.altsoft.altframework;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GsonInfo <T1, T2> {
    Gson gson;
    private Class<T1> type;
    private Class<T2> type2;

    public GsonInfo() throws IllegalAccessException, InstantiationException {
        gson = new Gson();
        type = (Class<T1>) type.newInstance();
        type2 = (Class<T2>) type2.newInstance();
    }

    public GsonInfo(Class<T1> type) {
        this.type = type;
        gson = new Gson();
    }

    public GsonInfo(Class<T1> type, Class<T2> type2) {
        this.type = type;
        this.type2 = type2;
        gson = new Gson();
    }

    /// Json  파일에 쓰기
    public void ConvertToJsonFile(T1 obj, String filePath) throws IOException {
        gson.toJson(obj, new FileWriter(filePath));
    }

    /// Json String로 변경
    public String ToString(T1 obj) {
        return gson.toJson(obj);
    }

    /// 파일에서 클래스로 변환
    public T1 ToFileObject(String filePath) throws FileNotFoundException {
        return gson.fromJson(new FileReader(filePath), type);
    }

    /// T2 오브젝트로 전환
    public T2 ToObject(String json) throws FileNotFoundException {
        return gson.fromJson(json, type2);
    }

    /// 파일을 Json Element로 변환
    public JsonElement ToJsonElement(String filePath) throws FileNotFoundException {
        return gson.fromJson(new FileReader(filePath), JsonElement.class);
    }

    /// 오브젝트 카피
    public T2 ToCopy(T1 obj) throws FileNotFoundException {
        return ToObject(ToString(obj));
    }
}