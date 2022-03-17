package com.cycling.cache;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * @ClassName: IncludeShiroFields
 * @Description: 序列化过滤器
 * @Author: qyz
 * @date: 2021/10/20 18:23
 * @Version: V1.0
 */


@JsonFilter("shiroFilter")
public interface IncludeShiroFields {
}
