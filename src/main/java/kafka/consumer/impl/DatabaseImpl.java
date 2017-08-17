package kafka.consumer.impl;

import kafka.consumer.bean.User;
import kafka.consumer.util.ConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by xiaotao on 2017/8/17.
 */
public class DatabaseImpl implements IDatabase<User> {
    public  void insert( User user) {
        Connection connection = ConnUtil.getConn();
        String sql = "insert into t_e_user (name,gender,age) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getGender());
            pstmt.setInt(3, user.getAge());
            int  i = pstmt.executeUpdate();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int delete(User t) {
        return 0;
    }

    public int update(User t) {
        return 0;
    }
}
