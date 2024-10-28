package com.techtech.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techtech.dto.ProductDTO;
import com.techtech.service.ProductService;


@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
		
	@GetMapping("/deleteProduct")
	public String bababa(@RequestParam String pid, Model model) {
		//String pid = req.getParameter("pid"); //RequestParam replaces this line
		
		productService.deleteById(Integer.parseInt(pid)); //deletes //add this method in the service layer
		
		//now I want to fetch the remaining data
		List<ProductDTO> productList = productService.findAll();
		model.addAttribute("productList", productList);
		model.addAttribute("message", "Product is deleted from Database");//to display a message
		return "addProduct";
	}
	
	@GetMapping({"/showAddProduct","/"})
	public String showAddProductPage(Model model) {
		//I want to show all the products when opening addproduct page.
		List<ProductDTO> productList = productService.findAll();
		model.addAttribute("productList", productList);
		return "addProduct";
	}
	
	@PostMapping("/addProduct")
	public String createProduct(@ModelAttribute ProductDTO productDTO, Model model) {

		//BELOW LINES ARE REPLACED BY @ModelAttribute ProductDTO productDTO
		/*String name = req.getParameter("name");
		String price = req.getParameter("price");
		String category = req.getParameter("category");
		String photo = req.getParameter("photo");
		ProductDTO productDTO = new ProductDTO();
		productDTO.setCategory(category);
		productDTO.setName(name);
		productDTO.setPhoto(photo);
		productDTO.setPrice(Double.parseDouble(price));*/
	
		productDTO.setDoe(new Timestamp(new Date().getTime()));
		productService.save(productDTO); //save in database
		
		List<ProductDTO> productList = productService.findAll();
		
		//now you want to send this productList to the jsp file -->webpage
		model.addAttribute("productList", productList);
		model.addAttribute("message", "Product is added into Database");//to display a message

		return "addProduct";
	}
	
	@GetMapping("/searchProduct")
	public String searchProduct(@RequestParam String stext, Model model) {
		
		//now I want to fetch the remaining data
		List<ProductDTO> productList = productService.searchProduct(stext);
		model.addAttribute("productList", productList);
		return "addProduct";
	}
	
	@GetMapping("/sorting")
	public String sortingBy(@RequestParam String attribute,@RequestParam String orderBy , Model model) {
		
		List<ProductDTO> productList = productService.findAll(); //findAll is memory
		
		if(attribute.equals("name") && orderBy.equals("asc")) {
			model.addAttribute("orderBy", "desc");
			Collections.sort(productList,Comparator.comparing(ProductDTO::getName));
		} else if(attribute.equals("name")) {
			model.addAttribute("orderBy", "asc");
			Collections.sort(productList,Comparator.comparing(ProductDTO::getName).reversed());
		}
		
		if(attribute.equals("category") && orderBy.equals("asc")) {
			model.addAttribute("orderBy", "desc");
			Collections.sort(productList,Comparator.comparing(ProductDTO::getCategory));
		} else if(attribute.equals("category")) {
			model.addAttribute("orderBy", "asc");
			Collections.sort(productList,Comparator.comparing(ProductDTO::getCategory).reversed());
		}
		
		model.addAttribute("productList", productList);
		return "addProduct";
	}

	@GetMapping("/api/chart")
	public ResponseEntity<byte[]> getBarChart() throws IOException {
		
		
		byte[] chartImage = generateBarChart();

		// Set HTTP Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		headers.setContentLength(chartImage.length);

		return new ResponseEntity<>(chartImage, headers, HttpStatus.OK);
	}
	
	// Generate the bar chart
	public byte[] generateBarChart() throws IOException {
			// Create dataset with String X-axis and double Y-axis
			CategoryDataset dataset = createDataset();
			// Create a bar chart
			JFreeChart chart = ChartFactory.createBarChart("Sales by Product", // Chart Title
									"Product", // X-axis label (String)
									"Sales (in $)", // Y-axis label (Double)
									dataset);
			
			
		// Step 3: Customize the Bar Renderer
	    CategoryPlot plot = chart.getCategoryPlot();
	    BarRenderer renderer = (BarRenderer) plot.getRenderer();
	    // Set different colors for each bar
	    renderer.setSeriesPaint(0, Color.ORANGE);
	    renderer.setSeriesPaint(1, Color.MAGENTA);

	    // Convert the chart to an image (PNG)
	    BufferedImage chartImage = chart.createBufferedImage(800, 600);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(chartImage, "png", baos);
		return baos.toByteArray();
	}
	
	// Create dataset with String as X-axis values and Double as Y-axis values
	private CategoryDataset createDataset() {
			
			//Mobile 8
			//Mobile, 4
			//Mobile, 12
			
			//T-Shirt -12
			
			//map = {Mobile,24} //added all the mobile price.
			
		List<ProductDTO> productList = productService.findAll();
		Map<String,Double> map =new HashMap<>();
		for(ProductDTO pe :productList){ //iterate the list
			String category=pe.getCategory(); //we are getting category and
			double currentPrice =pe.getPrice(); //price
			if(map.containsKey(category)) {
				double priceInMap=map.get(category); //fetching the current data
				map.put(category, priceInMap+currentPrice); //if category is there add new price for total
			}else {
				map.put(category, currentPrice); //if doesnt contain category, add category and price
			}
		}
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
		// Add data (Product name as X-axis and Sales value as Y-axis)
		map.forEach((category,price)->{
				dataset.addValue(price, "Sales", category); // X-axis: Product A, Y-axis: 25000.0
		});
		return dataset;
	}
}
