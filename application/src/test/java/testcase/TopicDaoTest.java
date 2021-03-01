package testcase;



import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

import ma.ensias.beans.Topic;
import ma.ensias.beans.User;
import ma.ensias.dao.DAOFactory;
import ma.ensias.dao.TopicDao;

class TopicDaoTest {
	
	public static TopicDao topicDao = DAOFactory.getInstance().getTopicDao();
	
	@Before
    public  void setup(){
		
        System.out.println("Starting Testing , all variables are set");
    }
	
	
	@Test
	void testCreate()
	{	
		User user = DAOFactory.getInstance().getUserDao().find(1);
		/*
		 * Test de creation de topic 
		 */
		Topic topic = new Topic("IA","Ce topic pour les gens qui sont interesse au IA","src/images/icons/ia","src/images/covers/ia",user);
		topicDao.create(topic,false);
		Topic topicFromDB = topicDao.find(topic.getId());
		assertEquals(topic.getId(),topicFromDB.getId());
		assertEquals(topic.getTitle(),topicFromDB.getTitle());
		assertEquals(topic.getDescription(),topicFromDB.getDescription());
		assertEquals(topic.getIconUrl(),topicFromDB.getIconUrl());
		assertEquals(topic.getCoverUrl(),topicFromDB.getCoverUrl());
	
	}
	@Test
	void testFind()
	{	
		User user = DAOFactory.getInstance().getUserDao().find(1);
		Topic topicExist = new Topic("IA","Ce topic pour les gens qui sont interesse au IA","src/images/icons/ia","src/images/covers/ia",user);
		

		assertEquals(topicDao.find(4).getTitle(),topicExist.getTitle());
	}
	
}
