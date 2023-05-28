package com.trip.server.util;

import com.trip.server.dto.PageDto;
import com.trip.server.dto.PageParamsDto;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

@UtilityClass
public final class PageUtil {

    public static final int MIN_PAGE = 0;

    public static final int MIN_SIZE = 1;

    public static final int MAX_SIZE = 50;

    public static final String PAGE_ERROR = "Номер страницы не может быть меньше " + MIN_PAGE;

    public static final String SIZE_ERROR = "На странице не может быть меньше " + MIN_SIZE + " элемента";

    public static <T, C> PageDto<C> toDto(ModelMapper mapper, Page<T> page, Class<C> contentType) {
        return PageDto.<C>builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .data(page.stream()
                        .map(e -> mapper.map(e, contentType))
                        .toList()
                )
                .build();
    }

    public static <T> PageImpl<T> paginate(List<T> content, Pageable pageable) {
        var pageRequest = PageRequest.of(
                Math.max(0, pageable.getPageNumber() - 1),
                pageable.getPageSize()
        );
        var page = new PagedListHolder<>(content);
        page.setPage(pageRequest.getPageNumber());
        page.setPageSize(pageRequest.getPageSize());

        if (page.getPageCount() < pageable.getPageNumber()) {
            return new PageImpl<>(Collections.emptyList(), pageRequest, content.size());
        }

        return new PageImpl<>(page.getPageList(), pageRequest, content.size());
    }

    public static <T, T1> Page<T1> mapContent(Page<T> page, List<T1> content) {
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Integer getPage(@Nullable Integer page) {
        return page == null ? MIN_PAGE : Math.max(page, MIN_PAGE);
    }

    public static Integer getSize(@Nullable Integer size) {
        return size == null ? MAX_SIZE : Math.max(Math.min(size, MAX_SIZE), MIN_SIZE);
    }

    public static PageRequest request(@Nullable Integer page, @Nullable Integer size) {
        return PageRequest.of(getPage(page), getSize(size));
    }

    public static PageRequest request(PageParamsDto pageParamsDto) {
        return request(pageParamsDto.getPage(), pageParamsDto.getSize());
    }

}
