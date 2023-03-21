package com.kakao.search.apiservice.constants;

public class ConstType {

  public enum SortType {
    accuracy("sim"),
    recency("date");

    private final String nType;

    SortType(String nType) {
      this.nType = nType;
    }

    public String toString() {
      return nType;
    }
  }
}
