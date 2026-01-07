package com.wmc.guiaremision.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllResponse<T> {
  private List<T> list;
  private int currentPage;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean hasNext;
  private boolean hasPrevious;
}
