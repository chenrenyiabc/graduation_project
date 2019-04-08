package com.bigdata.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSUtil {
	// 初始化配置对象
	private static Configuration conf = new Configuration();
	// 声明需要访问的集群地址
	private static String URI = "";
	// 声明操作文件系统的类
	private static FileSystem fs;

	/**
	 * 初始化工具类，指定需要操作的集群
	 * 
	 * @param hostName
	 */
	public HDFSUtil(String hostName) {
		URI = "hdfs://" + hostName + ":8020";
		// 从指定的集群读取配置
		FileSystem.setDefaultUri(conf, URI);
		try {
			// 使用读取到的配置实例
			fs = FileSystem.get(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回当前用户的家目录
	 * 
	 * @return
	 */
	public String getHomeDir() {
		return fs.getHomeDirectory().toString();
	}

	/**
	 * 创建文件夹，可多级
	 * 
	 * @param path
	 *            完整路径，不需要添加斜杠
	 * @param useHomeDir
	 *            不在用户家目录中创建
	 */
	public boolean mkdirs(String path, boolean useHomeDir) {
		try {
			if (useHomeDir) {
				fs.mkdirs(new Path(path));
			} else {
				fs.mkdirs(new Path("/" + path));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除文件夹或文件
	 * 
	 * @param path
	 */
	public boolean delete(String path, boolean recursive) {
		try {
			if (recursive) {
				return fs.delete(new Path(path), true);
			} else {
				return fs.delete(new Path(path), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
	}

	/**
	 * 文件重命名
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public void reNameDir(String oldPath, String newPath) {
		try {
			fs.rename(new Path(oldPath), new Path(newPath));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件上传
	 * 
	 * @param delSrc
	 *            是否删除原文件
	 * @param overwrite
	 *            是否覆盖目标文件
	 * @param srcs
	 *            原文件路径，可以指定多个路径
	 * @param dest
	 *            目标路径
	 */
	public boolean upLoad(boolean delSrc, boolean overwrite, String[] srcs, String dest) {
		try {
			if (srcs.length == 1) {
				fs.copyFromLocalFile(delSrc, overwrite, new Path(srcs[0]), new Path(dest));
			} else {
				Path[] paths = new Path[srcs.length];
				for (int i = 0; i < srcs.length; i++) {
					paths[i] = new Path(srcs[i]);
				}
				fs.copyFromLocalFile(delSrc, overwrite, paths, new Path(dest));	
			}return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 文件下载
	 * 
	 * @param delSrc
	 *            是否删除原文件
	 * @param src
	 *            原文件路径
	 * @param dest
	 *            目标路径
	 */
	public void downLoad(boolean delSrc, String src, String dest) {
		try {
			fs.copyToLocalFile(delSrc, new Path(src), new Path(dest));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将本地磁盘文件内容写入HDFS文件中
	 * @param src 源文件路径
	 * @param parentDir 目标文件父级目录
	 * @param fileName 目标文件名称
	 * @param overwrite 是否覆盖写入
	 * @return
	 */
	public boolean write(String src,String parentDir,String fileName,boolean overwrite) {
		// 判断源文件是否存在，如不存在则直接返回
		if (!new File(src).exists()) {
			System.out.println("源文件不存在");
			return false;
		}
		FSDataOutputStream fsDataOutputStream = null;
		boolean isDir = false;
		try {
			// 由于HDFS的特殊性，必须保证父级路径是一个目录，而不能只判断是否存在
			isDir = fs.isDirectory(new Path(parentDir));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!isDir) {// false -> 可能为文件也可能不存在
			try {
				// 尝试创建父级目录
				fs.mkdirs(new Path(parentDir));
			} catch (Exception e) {
				// 出现异常说明该路径下已经存在了文件 - 与目标文件夹文件相同
				e.printStackTrace();
				System.out.println("该路径不可用");
				return false;
			}
		}
		Path destPath = new Path(parentDir + File.separator + fileName);
		if (overwrite) {
			try {
				// 覆盖写入时使用create方法进行创建，指定覆盖参数为true
				fsDataOutputStream = fs.create(destPath,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			try {
				// 保证文件一定存在，如果已经存在返回false，不会重新创建
				fs.createNewFile(destPath);
				// 追加写入时使用append方法进行创建
				fsDataOutputStream = fs.append(destPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 初始化输入流，指定编码
		BufferedReader bufferedReader = null;
		Writer writer = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(src)), "UTF-8"));
			writer = new OutputStreamWriter(fsDataOutputStream, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		String temp = "";
		int line = 0;
		try {
			while((temp = bufferedReader.readLine()) != null) {
				bufferedWriter.write(temp);
				bufferedWriter.newLine();
				line ++;
				// 每一千行写入一次数据
				if (line % 1000 == 0) {
					bufferedWriter.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {
			bufferedWriter.flush();
			bufferedWriter.close();
			writer.close();
			bufferedReader.close();
			fsDataOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 从指定文件中读取数据
	 * @param path HDFS路径
	 */
	public String read(String path) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			// 使用open方法获得一个输入流
			FSDataInputStream fsDataInputStream =  fs.open(new Path(path));
			// 使用缓冲流读取文件内容
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsDataInputStream, "UTF-8"));
			String temp = "";
			while ((temp = bufferedReader.readLine()) != null) {
				stringBuffer.append(temp + "\n");
				System.out.println(temp);
			}
			bufferedReader.close();
			fsDataInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 获得某一路径下的文件信息
	 * @param path 待查看路径
	 * @return 文件信息列表-包含文件类型，文件大小，所有者，所在组，文件名称
	 */
	public List<String> getFileInfo(String path){
		List<String> infos = new ArrayList<>();
		try {
			// 通过FileSystem获得某一路径下的文件状态列表
			FileStatus[] fileStatus = fs.listStatus(new Path(path));
			for (FileStatus temp : fileStatus) {
				String info = "";
				// 判断文件类型
				if (temp.isDirectory()) {
					info += "目录\t" + "0" + "\t";
				}else {
					info += "文件\t" + sizeFormat(temp.getLen()) + "\t";
				}
				// 拼接文件信息
				info += temp.getOwner() + "\t" + temp.getGroup() + "\t" + temp.getPath().getName();
				infos.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	
	/**
	 * 文件大小单位换算
	 * @param length 默认获得的文件大小单位为Byte-字节
	 * @return 使用1024进行换算
	 */
	private String sizeFormat(long length) {
		long result = length;
		// 不足1024则单位为Byte
		if (result / 1024 == 0) {
			return result + "B";
		}else {
			result /= 1024;
			// 不足1024*1024则单位为KB，否则为MB
			if (result / 1024 == 0) {
				return result + "KB";
			}else {
				return result / 1024 + "MB";
			}
		}
	}
	
	/**
	 * 删除文件或者目录
	 * @param path 文件或者目录的路径
	 * @param recursive 是否递归删除
	 * */
	public boolean delete2(String path, boolean recursive){
		boolean flag = false;
		try {
			//如果需要递归删除
			flag = fs.delete(new Path(path), recursive);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 创建目录
	 * @param dirPath 目录完整路径
	 * @param isCreateInHomeDir 是否在家目录创建
	 * @return 是否创建成功
	 * */
	public boolean mkdirs2(String dirPath, boolean isCreateInHomeDir){
		boolean flag = false;
		try {
			//在家目录创建
			if(isCreateInHomeDir){
				String path = getHomeDir();
				//判断是否以/开头
				if(dirPath.startsWith("/")){
					path += dirPath;
				}else{
					path += "/" + dirPath;
				}
				flag = fs.mkdirs(new Path(path));
			}else{//不在家目录创建
				flag = fs.mkdirs(new Path(dirPath));
			}
			flag = fs.mkdirs(new Path(dirPath));
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	

}
