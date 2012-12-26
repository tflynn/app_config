package com.verymuchme.appconfig;

public class NullString {

  public static final String INSTANCE = new String("bafcab43-96a2-420f-88f9-d38a2c06308d");
  
  public static String getInstance() {
    return NullString.INSTANCE;
  }
  
  private NullString() {
    
  }
  
  public boolean equals(Object other) {
    return NullString.INSTANCE == other;
  }
  
  public String toString() {
    return "null";
  }
}
