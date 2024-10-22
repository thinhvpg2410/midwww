package shopping.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shopping.dao.ProductDAO;
import shopping.daoImpl.ProductDAOImp;
import shopping.entities.Product;

import java.io.IOException;

import javax.sql.DataSource;

/**
 * Servlet implementation class ProductController
 */
@WebServlet(urlPatterns = {"/product"})
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(name = "jdbc/shopping")
    private DataSource dataSource;
	
	private ProductDAO productDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		productDAO = new ProductDAOImp(this.dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		Product p1 = new Product("Product 1", 50000, "image1.png");
//		productDAO.addProduct(p1);
//		Product p2 = new Product("Product 2", 20000, "image2.png");
//		productDAO.addProduct(p2);
//		Product p3 = new Product("Product 3", 40000, "image3.jpeg");
//		productDAO.addProduct(p3);
//		Product p4 = new Product("Product 4", 80000, "image4.jpeg");
//		productDAO.addProduct(p4);
//		Product p5 = new Product("Product 5", 100000, "image5.png");
//		productDAO.addProduct(p5);
		
		request.setAttribute("products", productDAO.findAll());
		request.getRequestDispatcher("views/product/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
