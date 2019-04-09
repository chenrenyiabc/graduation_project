package com.bigdata.service.algorithm;

import com.bigdata.bean.Algorithm;
import com.bigdata.dao.algorithm.AlgorithmDao;

import java.util.List;

public class AlgorithmService {

    AlgorithmDao ad = new AlgorithmDao();

    public List<Algorithm> getAlgorithm() {
        return  ad.getAlgorithm();
    }
}
