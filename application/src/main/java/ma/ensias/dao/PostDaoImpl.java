package ma.ensias.dao;

import static ma.ensias.dao.DAOusef.closeConnectionItems;
import static ma.ensias.dao.DAOusef.initQueryPrepared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ma.ensias.beans.Content;
import ma.ensias.beans.Post;
import ma.ensias.beans.Topic;
import ma.ensias.beans.User;

public class PostDaoImpl implements PostDao {
	
	private static final String SQL_INSERT = "INSERT INTO topic(title,likes,dislikes,date,content,type,topic,user) VALUES (?,?,?,?,?,?,?,?) ";
	private static final String SQL_SELECT_BY_ID = "SELECT title,likes,dislikes,date,type,topic,user FROM post WHERE id = ?";
	//private static final String SQL_UPDATE = "UPDATE topic SET title = ?, description = ?, iconUrl = ?, coverUrl = ?  WHERE id = ?";
	
	
	private DAOFactory daoFactory;
	
	PostDaoImpl(DAOFactory daoFactory)
	{
		this.daoFactory = daoFactory;
	}
	
	private static Post map(ResultSet resultset) throws SQLException {
		Post post = new Post();
		post.setId(resultset.getInt("id"));
		post.setTitle(resultset.getString("title"));		
		post.setLikes(resultset.getInt("likes"));
		post.setDislikes(resultset.getInt("dislikes"));
		post.setDate(resultset.getDate("date"));
		DAOFactory daoFactory = DAOFactory.getInstance();
		post.setUser(new UserDaoImpl(daoFactory).find(resultset.getInt("user")));
		post.setTopic(new TopicDaoImpl(daoFactory).find(resultset.getInt("topic")));
		// TODO : Content
		return post;
	}

	@Override
	public void create(Post post) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement  preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		
		String title = post.getTitle();
		int likes = post.getLikes();
		int dislikes = post.getDislikes();
		Date date = post.getDate();
		int idContent = post.getContent().getId();
		int type = post.getType();
		int idTopic = post.getTopic().getId();
		int idUser = post.getUser().getId();
		
		
		try 
		{	
			connexion = daoFactory.getConnection();
			preparedStatement = initQueryPrepared(connexion,SQL_INSERT,true,title,title,likes,dislikes,date,idContent,type,idTopic,idUser);
			int statut = preparedStatement.executeUpdate();
			if(statut == 0)
			{
				throw new DAOException("Post creation error , no line was inserted");
			}
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if(valeursAutoGenerees.next() )
			{
				post.setId(valeursAutoGenerees.getInt(1));
				
			}
			else
			{
				throw new DAOException("Post creation error in DB , no auto generated ID was returned");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			 closeConnectionItems(preparedStatement,connexion); 
		}
	}

	@Override
	public Post find(String title, Content content, Topic topic, User user) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object... fields) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Post post) throws DAOException {
		// TODO Auto-generated method stub

	}

}