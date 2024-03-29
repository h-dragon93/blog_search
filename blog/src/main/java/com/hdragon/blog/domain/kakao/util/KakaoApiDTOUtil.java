package com.hdragon.blog.domain.kakao.util;

import com.hdragon.blog.domain.kakao.api.dto.KakaoApiRequestDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.Valid;

public class KakaoApiDTOUtil {

    public static KakaoApiRequestDTO getRequestDTO(KakaoApiRequestDTO RequestDTO, String query, String sort, Integer page, Integer size) {

        RequestDTO = RequestDTO.builder().
                                query(query).
                                sort(sort == null ? RequestDTO.getSort() : sort).
                                page(page == null ? RequestDTO.getPage() : page).
                                size(size == null ? RequestDTO.getSize() : size).
                                build();

        return RequestDTO;
    }

}
