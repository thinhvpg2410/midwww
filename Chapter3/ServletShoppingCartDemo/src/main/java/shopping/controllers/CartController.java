package shopping.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shopping.dao.ProductDAO;
import shopping.daoImpl.ProductDAOImp;
import shopping.entities.ItemCart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class CartController
 */
@WebServlet(urlPatterns = {"/cart", "/cart*"})
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/shopping")
    private DataSource dataSource;
	
	private ProductDAO productDAO;
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
        super();
        // TODO Auto-generated constructor stub
        System.out.println(dataSource);
       
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
		String action = request.getParameter("action") != null ? request.getParameter("action") : "";
		 
		switch(action) {
			case "buy":
				doGetBuy(request, response);
				break;
			case "remove":
				doGetRemove(request, response);
				break;
			default:
				doGetDisplayCart(request, response);
				break;
			
		}
	}

	/**
	 * Display Cart
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doGetDisplayCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("views/cart/index.jsp").forward(request, response);
	}
	
	/**
	 * Remove cart
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doGetRemove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<ItemCart> cart = (List<ItemCart>) session.getAttribute("cart");
		int index = isProductExisting(Integer.parseInt(request.getParameter("id")), cart);
		cart.remove(index);
		session.setAttribute("cart", cart);
		response.sendRedirect("cart");
	}
	
	/**
	 * Buy product
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doGetBuy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		List<ItemCart> cart = null;
		
		if (session.getAttribute("cart") == null) {
			cart = new ArrayList<ItemCart>();
		} else {
			cart = (List<ItemCart>) session.getAttribute("cart");
		}
		
		int index = isProductExisting(Integer.parseInt(request.getParameter("id")), cart);
		if (index == -1) {
			cart.add(new ItemCart(productDAO.getById(Integer.parseInt(request.getParameter("id"))), 1));
		} else {
			int quantity = cart.get(index).getQuantity() + 1;
			cart.get(index).setQuantity(quantity);
		}
		session.setAttribute("cart", cart);
		
		response.sendRedirect("cart");
	}

	/**
	 * Check Product exist or not
	 * @param id
	 * @param cart
	 * @return integer
	 */
	private int isProductExisting(int id, List<ItemCart> cart) {
		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(i).getProduct().getId() == id) {
				return i;
			}
		}
		return -1;
	}
}
