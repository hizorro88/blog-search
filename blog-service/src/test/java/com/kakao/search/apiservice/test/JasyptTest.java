package com.kakao.search.apiservice.test;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptTest {

  private static final String TEST_STRING_1 = "ID test";

  @Autowired
  StringEncryptor jasyptStringEncryptor;

  @Test
  void testEncrypt() {
    String encrypted1 = jasyptStringEncryptor.encrypt(TEST_STRING_1);
    String result = jasyptStringEncryptor.decrypt(encrypted1);
    System.out.println("encrypted(" + TEST_STRING_1 + "): " + encrypted1);

    assert (TEST_STRING_1.equals(result));
  }
}
