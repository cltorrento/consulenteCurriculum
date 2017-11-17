package it.f2informatica.consulenteCurriculum.domain;

public class Curriculum {

	private String base64;
	private String filename;
	private long filesize;
	private String filetype;

	public Curriculum() {

	}

	public Curriculum(String base64, String filename, long filesize, String filetype) {
		super();
		this.base64 = base64;
		this.filename = filename;
		this.filesize = filesize;
		this.filetype = filetype;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	@Override
	public String toString() {
		return "{\"base64\":\"" + base64 + "\",\"filename\":\"" + filename + "\",\"filesize\":\"" + filesize
				+ "\",\"filetype\":\"" + filetype + "\"}";
	}

}
