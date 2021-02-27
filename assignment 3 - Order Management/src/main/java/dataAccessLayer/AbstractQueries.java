package dataAccessLayer;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the abstract class that characterizes the reflection concept, defining the insertion,deletion in a general way that
 * can be then adapted specifically for each class that extends it;
 *
 * @param <T> which is an object of wither type product, client or order
 */
public class AbstractQueries<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractQueries.class.getName());

    private final Class<T> type;

    public AbstractQueries() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * This method creates a conditioned query that will be later executed
     *
     * @param field which names the field where the condition should be searched for
     * @return it returns the string that is used as a statement later in the database (a query)
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("* ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }

    /**
     * This method executes the earlier specified query with the given string (a name in our case) in order to find a specific object in a table
     *
     * @param searched_name is a string that is passed in order to serve as the searched string in the query
     * @param field which names the field where the condition should be searched for
     * @return it returns the first object that fits the characteristics searched for by the query
     */
    public T findByName(String searched_name, String field) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, searched_name);
            resultSet = statement.executeQuery();
            List<T> list =createObjects(resultSet) ;
            if(!list.isEmpty())
            return list.get(0);
            else return null;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());

        } catch(IndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
            throw new IllegalArgumentException("Invalid product/customer name: " + searched_name);
        }

        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * This method creates objects based on the resultSet given as a parameter
     *
     * @param resultSet it is a list formed of all the objects created
     * @return it returns the created list of objects
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor((String)field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *This method constructs the needed query that will be then used when we want to insert data into a table
     *
     * @param fields represents the fields of the table in the database
     * @return the insert query needed to insert data into the table
     */
    private String createInsertStatement(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName() + "(");
        String prefix = "";
        for (Field f : fields) {
            if (f.getName().equals("idclient") || f.getName().equals("idorder") || f.getName().equals("idproduct")) {
                continue;
            }
            sb.append(prefix);
            prefix = ", ";
            sb.append(f.getName());
        }
        sb.append(") VALUES (");
        for (int i = 0; i < fields.length - 2; i++)
            sb.append("? ,");
        sb.append("?) ");
        return sb.toString();
    }

    /**
     * The method inserts a new object into a table in our database;
     * Because the ID is autoincrementing, it jumps over it and inserts only the rest of the fields
     *
     * @param element represents the element that we want to insert into the table
     */
    public void insert(T element) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Field[] fields = type.getDeclaredFields();
        String query = createInsertStatement(fields);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            for (Field f : fields) {
                if(f.getName().equals("idclient") || f.getName().equals("idorder") || f.getName().equals("idproduct")) {
                    continue;
                }
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor((String)f.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object fieldValue = method.invoke(element);
                if (fieldValue instanceof Integer) {
                    statement.setInt(i, (Integer) fieldValue);
                } else if (fieldValue instanceof Float) {
                    statement.setFloat(i, (Float) fieldValue);
                } else {
                    statement.setString(i, (String) fieldValue);
                }
                i++;
            }
            statement.execute();
        } catch(SQLIntegrityConstraintViolationException e) {
            LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
            throw new IllegalArgumentException("Invalid reference to another objectId");
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * The method constructs the deletion query used in the following method in order to delete an object from a table
     *
     * @param searchFieldName represents the field that determines the property we are looking for in the object to be deleted
     * @return it returns the query used in deletion
     */
    public String createDeleteStatement(String searchFieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(searchFieldName);
        sb.append("= ?");
        return sb.toString();
    }

    /**
     * The method deletes a certain object from the database
     *
     * @param element is the type of the object we want to delete from the table
     * @param name represents the field name we are looking for(the name of the product/client)
     */
    public void delete(T element,String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteStatement("name");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            Field[] fields = type.getDeclaredFields();
            statement.setString(1, name);
            statement.execute();
        }catch(SQLIntegrityConstraintViolationException e){
            throw new IllegalArgumentException("Cannot delete object as it is part of order");
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:delete " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    private String createUpdateStatement(Field[] fields, String searchField) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (int i=0; i<fields.length-1; i++) {
            Field f = fields[i];
            if (f.getName().equals("idclient") || f.getName().equals("idproduct") || f.getName().equals("idorder")) {
                continue;
            }
            sb.append(f.getName());
            sb.append("= ?,");
        }
        sb.append(fields[fields.length -1].getName());
        sb.append("= ?");
        sb.append(" WHERE ");
        sb.append(searchField+"= ?");
        return sb.toString();
    }

    public void update(T element) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Field[] fields = type.getDeclaredFields();
        String query = createUpdateStatement(fields, "name");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            String s = "";
            for(Field f: fields) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor((String)f.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object fieldValue = method.invoke(element);
                if(f.getName().equals("name"))  {
                    s = (String) fieldValue;
                    continue;
                }
                if (fieldValue instanceof Integer) {
                    statement.setInt(i, (Integer) fieldValue);
                } else if (fieldValue instanceof Float) {
                    statement.setFloat(i, (Float) fieldValue);
                } else {
                    statement.setString(i, (String) fieldValue);
                }
                i++;
            }
            statement.setString(i, s);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:update " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
