package fit.iuh.dao;

import java.util.List;

import fit.iuh.models.DienThoai;

public interface DienThoaiDAO {
	public List<DienThoai> findAll();
	public DienThoai getById(int id);
	public void addDienThoai(DienThoai dt);
	public void remove(DienThoai dt);
	public void clearAll();
	public List<DienThoai> findByName(String tenDT);
}
