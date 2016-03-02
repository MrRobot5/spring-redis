package x.y.dao;

import java.util.List;

import x.y.model.User;

public interface UserDao {

	public boolean add(User user);
	
	public boolean add(List<User> users);
	
	public void delete(String key);
	
	public void delete(List<String> keys);
	
	public boolean update(User user);
	
	public User get(String key);
	
}
