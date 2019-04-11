package com.bigdata.service.algorithm;

import com.bigdata.bean.Algorithm;
import com.bigdata.dao.algorithm.AlgorithmDao;

import java.util.List;

public class AlgorithmService {

    AlgorithmDao ad = new AlgorithmDao();

    public List<Algorithm> getAlgorithm() {
        return ad.getAlgorithm();
    }

    public Algorithm getAlgorithmById(Integer algoId) {
        return ad.getAlgorithmPath(algoId);
    }

    public Boolean updateAlgorithm(String algoId, String algorithm_name, String algorithm_path) {
        return ad.updateAlgorithm(algoId, algorithm_name, algorithm_path);
    }

    public Boolean newAlgorithm(String algorithm_name, String algorithm_path) {
        return ad.newAlgorithm(algorithm_name, algorithm_path);
    }

    public Boolean deleteAlgorithm(String algoId) {
        return ad.deleteAlgorithm(algoId);
    }
}
