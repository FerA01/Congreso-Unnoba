package com.ar.unnoba.congresos.Service;

import java.util.List;

public interface IPagingService {
    public List<Integer> getPagingRange(int current, int total, int lenght);
}
