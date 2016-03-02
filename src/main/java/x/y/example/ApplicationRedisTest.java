package x.y.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import x.y.dao.UserDao;
import x.y.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-base.xml"})
public class ApplicationRedisTest {

	@Autowired
	private UserDao userDao;
	
	/**
	 * 27629
	 * 2015年11月19日 上午10:47:59
	 */
//	@Test
	public void testAddUsers1() {
		List<User> list = new ArrayList<User>();
		for (int i = 10; i < 50000; i++) {
			User user = new User();
			user.setId("user" + i);
			user.setName("redis" + i);
			list.add(user);
		}
		long begin = System.currentTimeMillis();
		for (User user : list) {
			userDao.add(user);
		}
		System.out.println(System.currentTimeMillis() - begin);
	}
	
	/**
	 * 461
	 * 2015年11月19日 上午10:49:12
	 */
//	@Test
	public void testAddUsers2() {
		List<User> list = new ArrayList<User>();
		for (int i = 0; i < 50000; i++) {
			User user = new User();
			user.setId("user" + i);
			user.setName("redis" + i);
			list.add(user);
		}
		long begin = System.currentTimeMillis();
		userDao.add(list);
		System.out.println(System.currentTimeMillis() - begin);
	}
	
//	@Test
	public void testDeleteUsers3() {
		List<String> list = new ArrayList<String>();
		for (int i = 10; i < 50000; i++) {
			list.add("user" + i);
		}
		long begin = System.currentTimeMillis();
		userDao.delete(list);
		System.out.println(System.currentTimeMillis() - begin);
	}
	
	@Test
    public void testGetUser() {
        String id = "user2015";
        User user = userDao.get(id);
        Assert.assertNotNull("数据丢失", user);
        Assert.assertEquals("数据预期不一致", user.getName(), "redis2015");
    }
	

	
}
