package x.y.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import x.y.model.User;

/**
 * 
 * Tips : Maven Download Sources
 * @author yangp
 * 2015年11月19日 上午10:26:32
 */
public class UserDaoImpl extends AbstractBaseRedisDao<String, User> implements UserDao {

	public boolean add(final User user) {
		boolean result = this.redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection conn) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] id = serializer.serialize(user.getId());
				byte[] name = serializer.serialize(user.getName());
				return conn.setNX(id, name);
			}
		});
		return result;
	}

	public boolean add(final List<User> users) {
		Assert.notEmpty(users);
		
		boolean result = this.redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				for (User user : users) {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] id = serializer.serialize(user.getId());
					byte[] name = serializer.serialize(user.getName());
					connection.setNX(id, name);
				}
				return true;
			}
		}, false, true);
		return result;
	}

	public void delete(String key) {
		this.redisTemplate.delete(key);

	}

	public void delete(List<String> keys) {
		this.redisTemplate.delete(keys);

	}

	public boolean update(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public User get(final String key) {
		User result = this.redisTemplate.execute(new RedisCallback<User>() {

			public User doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] id = serializer.serialize(key);
				byte[] value = connection.get(id);
				if(value == null) return null;
				String name = serializer.deserialize(value);
				return new User(key, name);
			}
		});
		
		return result;
	}

}
