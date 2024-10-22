package packagename.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import packagename.dao.UserDAO;
import packagename.daoImpl.UserDAOImpl;
import packagename.models.User;
import packagename.utils.EntityManagerFactoryUtil;


/**
 * Servlet implementation class UserControllers
 */
@WebServlet("/UserControllers")
public class UserControllers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EntityManagerFactoryUtil entityManageFactory;
	private UserDAO userDao;
    /**
     * Default constructor. 
     */
	
    public UserControllers() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    public void init(ServletConfig config) throws ServletException {
    	// TODO Auto-generated method stub
    	super.init(config);
		this.entityManageFactory = new EntityManagerFactoryUtil();
		this.userDao = new UserDAOImpl(this.entityManageFactory.getEntityManager());
    }
    @Override
    public void destroy() {
    	// TODO Auto-generated method stub
    	this.entityManageFactory.close();
    	super.destroy();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action") != null ? request.getParameter("action") : "";
		 System.out.println(action);
		 switch (action) {
			case "new":
				showRegisterForm(request, response);
				break;
			case "delete":
               deleteUser(request, response);
               break;
           case "edit":
               showEditForm(request, response);
               break;
           default:
				listUser(request, response);
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String action = request.getParameter("action") != null ? request.getParameter("action") : "";
		 switch (action) {
			case "insert":
                insertUser(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
			default:
				listUser(request, response);
				break;
		}
	}
	
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> listUser = userDao.findAll();
        System.out.println(listUser);
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/user/list.jsp");
        dispatcher.forward(request, response);
    }
	
	 private void showRegisterForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/user/add.jsp");
        dispatcher.forward(request, response);
     }
	 
	 private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = this.userDao.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/user/edit.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
     }
	 
	 private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User newUser = new User(name, email, country);
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(newUser);
        
        if (violations.isEmpty()) {
	        this.userDao.save(newUser);
	        response.sendRedirect("users");
        }
        else {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("views/user/add.jsp");
            
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(violation -> {
            	stringBuilder.append(violation.getPropertyPath() + ": " + violation.getMessage());
            	stringBuilder.append("<br />");
            });
            
            request.setAttribute("user", newUser);
            request.setAttribute("errors", stringBuilder);
            dispatcher.forward(request, response);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        
        User user = new User(id, name, email, country);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        
        if (violations.isEmpty()) {
        	 this.userDao.update(user);
             response.sendRedirect("users");
        }
        else {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("views/user/edit.jsp");
            
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(violation -> {
            	stringBuilder.append(violation.getPropertyPath() + ": " + violation.getMessage());
            	stringBuilder.append("<br />");
            });
            
            request.setAttribute("user", user);
            request.setAttribute("errors", stringBuilder);
            dispatcher.forward(request, response);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDao.delete(id);
        response.sendRedirect("users");
    }

}
