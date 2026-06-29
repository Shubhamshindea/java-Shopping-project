package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductDAO productDAO;
    private ProductVariantDAO variantDAO;

    @Override
    public void init() {
        productDAO = new ProductDAOImpl();
        setVariantDAO(new ProductVariantDAOImpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);

    	if (session == null || session.getAttribute("user") == null) {
    	    response.sendRedirect("login");
    	    return;
    	}
        try {

            System.out.println("ProductDetailsServlet HIT");

            String idParam = request.getParameter("id");

            if (idParam == null || idParam.isEmpty()) {
                response.getWriter().println("Product ID missing");
                return;
            }

            int productId = Integer.parseInt(idParam);

            Product product = productDAO.getProductById(productId);

            if (product != null) {
                request.setAttribute("product", product);

                request.getRequestDispatcher("/WEB-INF/views/product-details.jsp")
                       .forward(request, response);
            } else {
                response.getWriter().println("Error loading product details");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public ProductVariantDAO getVariantDAO() {
		return variantDAO;
	}

	public void setVariantDAO(ProductVariantDAO variantDAO) {
		this.variantDAO = variantDAO;
	}
}