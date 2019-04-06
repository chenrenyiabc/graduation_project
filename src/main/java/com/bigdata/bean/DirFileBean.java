package com.bigdata.bean;

public class DirFileBean {
	private String dirFile;
	private String filesize;
	private String owner;
	private String ownerGroup;
	private String fileName;

	public DirFileBean(String dirFile, String filesize, String owner, String ownerGroup, String fileName) {
		super();
		this.dirFile = dirFile;
		this.filesize = filesize;
		this.owner = owner;
		this.ownerGroup = ownerGroup;
		this.fileName = fileName;
	}

	public String getDirFile() {
		return dirFile;
	}

	public void setDirFile(String dirFile) {
		this.dirFile = dirFile;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerGroup() {
		return ownerGroup;
	}

	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "DirFileBean [dirFile=" + dirFile + ", filesize=" + filesize + ", owner=" + owner + ", ownerGroup="
				+ ownerGroup + ", fileName=" + fileName + "]";
	}

}
