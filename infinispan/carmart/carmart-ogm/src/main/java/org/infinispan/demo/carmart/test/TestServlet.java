package org.infinispan.demo.carmart.test;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.infinispan.demo.carmart.model.Car;
import org.infinispan.demo.carmart.model.Car.CarType;
import org.infinispan.demo.carmart.model.Car.Country;

@WebServlet("/test.do")
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = -3519707359311408811L;
	
	@PersistenceUnit(unitName = "org.infinispan.demo.carmart")
	private EntityManagerFactory factory;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		addNewCar();
		showCarDetails("FML 23-25");
		getCarList();
		removeCar("FML 23-25");
	}
	
	private void removeCar(String numberPlate) {
		EntityManager em = factory.createEntityManager();
		Car car = em.find(Car.class, numberPlate);
		em.remove(car);
		em.close();
		System.out.println("Remove Car " + numberPlate);
	}

	private void getCarList() {
		EntityManager em = factory.createEntityManager();
		Query query = em.createQuery("SELECT * FROM Car");  
        List result = query.getResultList();
        em.close();
        System.out.println("Get Car list " + result);
	}

	private void showCarDetails(String numberPlate) {
		EntityManager em = factory.createEntityManager();
		Car car = em.find(Car.class, numberPlate);
		em.close();
		System.out.println("Show Car Details " + car);
    }

	private void addNewCar() {
		EntityManager em = factory.createEntityManager();
		Car c = new Car("Ford Focus", 1.6, CarType.COMBI, "white", "FML 23-25", Country.CHN);
        em.persist(c);
        em.close();
        System.out.println("Add a new car " + c);
	}

}
