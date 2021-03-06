package com.bigdata.bean;

public class Algorithm {
	
	
	public Algorithm() {
		
	}
	
	
	public Algorithm(String algorithm_name, String algorithm_path) {
		this.algorithm_name = algorithm_name;
		this.algorithm_path = algorithm_path;
	}


	public Algorithm(Integer id, String algorithm_name, String algorithm_path) {
		this.id = id;
		this.algorithm_name = algorithm_name;
		this.algorithm_path = algorithm_path;
	}

	public Algorithm(Integer id, String algorithm_name, String algorithm_path, String algorithm_class) {
		this.id = id;
		this.algorithm_name = algorithm_name;
		this.algorithm_path = algorithm_path;
		this.algorithm_class = algorithm_class;
	}

	// 算法id
	private Integer id;
	// 算法名
	private String algorithm_name;
	// 算法路径
	private String algorithm_path;
	// 算法主类
	private String algorithm_class;

	public String getAlgorithm_class() {
		return algorithm_class;
	}

	public void setAlgorithm_class(String algorithm_class) {
		this.algorithm_class = algorithm_class;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAlgorithm_name() {
		return algorithm_name;
	}
	public void setAlgorithm_name(String algorithm_name) {
		this.algorithm_name = algorithm_name;
	}
	public String getAlgorithm_path() {
		return algorithm_path;
	}
	public void setAlgorithm_path(String algorithm_path) {
		this.algorithm_path = algorithm_path;
	}

	@Override
	public String toString() {
		return "Algorithm{" +
				"id=" + id +
				", algorithm_name='" + algorithm_name + '\'' +
				", algorithm_path='" + algorithm_path + '\'' +
				", algorithm_class='" + algorithm_class + '\'' +
				'}';
	}
}
