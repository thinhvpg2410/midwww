package shopping.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import shopping.dao.ProductDAO;
import shopping.entities.Product;


public class ProductDAOImp implements ProductDAO {
		
	private DataSource datasource;
	
	public ProductDAOImp(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public List<Product> findAll() {
		
		String sql = "SELECT * FROM product";
		List<Product> list = new ArrayList<Product>();
		try (
				Connection con = this.datasource.getConnection();
				PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
		) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Double price = rs.getDouble("price");
				String image = rs.getString("image");
				list.add(new Product(id, name, price,image));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Product getById(int id) {
		
		String sql = "SELECT * FROM product WHERE id=?";
		Product p = null;
		try (
				Connection con = this.datasource.getConnection();
				PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			){
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					String name = rs.getString("name");
					Double price = rs.getDouble("price");
					String image = rs.getString("image");
					p = new Product(id, name, price,image);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public void addProduct(Product p) {
		String sql = "INSERT INTO product (name, price, image) VALUES (?,?,?)";
		try (
				Connection con = this.datasource.getConnection();
				PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		){
			ps.setString(1, p.getName());
			ps.setDouble(2, p.getPrice());
			ps.setString(3, p.getImage());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
