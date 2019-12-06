/* IST & CC CLOSSET Marine LENOIR Guillaume */ 
package com.example.carservice;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;



import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;




import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@RestController
public class CarRentalService {
	
	private List<Car> cars = new ArrayList<Car>();
	
	public CarRentalService() {
		cars.add(new Car("11AA22", "Ferrari", 1000));
		cars.add(new Car("33BB44", "Porshe", 2222));
	/*	cars.add(new Car ("45BB35", "BMW",3333));*/
	}

	/*-----Affichage des voitures -----*/
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/cars", method=RequestMethod.GET) 
	@ResponseStatus(HttpStatus.OK) 
	public List<Car> getListOfCars(){
		return cars;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/cars/{plateNumber}", method=RequestMethod.GET) 
	@ResponseStatus(HttpStatus.OK) 
	public Car getListOfCars(@PathVariable(value="plateNumber") String plateNumber){
		for(Car car: cars) {
			if(car.getPlateNumber().equals(plateNumber)) {
				return car;
			}
		}
		return null;
	}

	/* -----ajout voiture------*/ 
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/cars", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public Car addCar(@RequestBody Car car) throws Exception{
		cars.add(car);
		
			try{		
			/*-------------------------------------Queues-------------------------------------*/
				
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");
			Queue queue = (Queue) applicationContext.getBean("queue");
			QueueConnection connection = factory.createQueueConnection();
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueSender sender = session.createSender(queue);
			ObjectMessage aCar = session.createObjectMessage(car);
			sender.send(aCar);
			session.close();
			connection.close();	
			
			/* --------------------------------Topic------------------------------------
			 * 
			TopicConnectionFactory factoryTopic = (TopicConnectionFactory) applicationContext.getBean("connectionFactory");
			Topic topic = (Topic) applicationContext.getBean("topic");
			TopicConnection connection = factoryTopic.createTopicConnection();			
			TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			TopicPublisher sender = session.createPublisher(topic);
			Message message = session.createTextMessage("coucou topic");
			sender.send(message);
			session.close();
			connection.close();*/
			
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		return car;
	}
	
/* ------------------------------------- Debut de redaction code qui a plus de fonctionnalite et plus clean avec l'utilisation de la classe car List-----------------
 
    * ---------------get all cars-----------------*
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Car> listOfCars(){
        List<Car> l = CarList.getCars();  
        return l;
    }
    
    *----------------post a car--------------------*
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/cars", method = RequestMethod.POST)
    public ResponseEntity<String> addCar(@RequestBody Car newCar) {
        List<Car> l = CarList.getCars();
        
        Car myCar = CarList.getCar(newCar.getPlateNumber());
        *---------if the car doesn't exists yet--------*
        if(myCar == null) {
            l.add(newCar);
           CarList.setCars(l);
        }   
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
   
     *----------------delete a car--------------------*
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/cars/{plateNumber}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("plateNumber") String plateNumber) throws Exception{
        CarList.deleteCar(plateNumber);
    }
    
     *----------------rent a car--------------------*
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/cars/{plateNumber}/rent", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void rent(@PathVariable("plateNumber") String plateNumber) throws Exception{
        Car myCar = CarList.getCar(plateNumber);
        if(myCar != null) myCar.setStatus(1);
    }
    
     *----------------bring back a car--------------------*
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/cars/{plateNumber}/bringBack", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void bringBack(@PathVariable("plateNumber") String plateNumber) throws Exception{
        Car myCar = CarList.getCar(plateNumber);
        if(myCar != null) myCar.setStatus(0);
    }
 
 
 */
}




