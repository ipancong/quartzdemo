package com.example.quartz.demo.utils;

import java.util.List;
import java.util.Map;

public class StringUtils {

  /**
   * 判空
   *
   * @param str
   * @return
   */
  public boolean isEmpty(String str) {
    return (str == null) || (str.length() == 0) || (str.equals(""));
  }

  /**
   * 去除空格
   *
   * @param str
   * @return
   */
  public String trim(String str) {
    return str == null ? null : str.trim();
  }

  /**
   * 获取map参数
   *
   * @param map
   * @return
   */
  public String getMapString(Map<String, String> map) {
    String result = "";
    for (Map.Entry entry : map.entrySet()) {
      result += entry.getValue() + " ";
    }
    return result;
  }

  /**
   * 获取list参数
   *
   * @param list
   * @return
   */
  public String getListString(List<String> list) {
    String result = "";
    for (String s : list) {
      result += s + " ";
    }
    return result;
  }
}
