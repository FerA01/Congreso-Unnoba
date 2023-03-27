package com.ar.unnoba.congresos.Service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PagingService implements IPagingService {

    @Override
    public List<Integer> getPagingRange(int current, int total, int lenght) {

        int start, end;

        start = current - (lenght / 2);
        start = Math.max(start, 1);

        end = start + lenght - 1;
        if (end > total) {
            end = total;
            start = end - lenght + 1;
        }
        start = Math.max(start, 1);

        return IntStream.rangeClosed(start, end).boxed()
                .collect(Collectors.toList());

    }
}
