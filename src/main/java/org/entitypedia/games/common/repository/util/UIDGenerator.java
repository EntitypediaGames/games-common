package org.entitypedia.games.common.repository.util;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.Random;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class UIDGenerator implements IdentifierGenerator {

//    private static final Logger log = LoggerFactory.getLogger(UIDGenerator.class);

    private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int UID_LENGTH = 8;

//    private String sql;

//    /**
//     * The table name parameter.
//     */
//    public static final String TABLE_NAME = "tableName";

//    /**
//     * The column name parameter.
//     */
//    public static final String COLUMN_NAME = "columnName";

    public UIDGenerator() {
    }

//    public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
//        String tableName = (String) params.get(TABLE_NAME);
//        String columnName = (String) params.get(COLUMN_NAME);
//        sql = "SELECT 1 FROM " + tableName + " where " + columnName + " = ? limit 1;";
//    }

    public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
        //allow low risk of error.
        return getUID();

/*
        try {
            PreparedStatement st = session.getBatcher().prepareSelectStatement(sql);
            String result = getUID();
            try {
                st.setString(1, result);
                ResultSet rs = st.executeQuery();
                try {
                    boolean empty = rs.next();
                    while (!empty) {
                        result = getUID();
                        st.setString(1, result);
                        rs = st.executeQuery();
                        empty = rs.next();
                    }
                } finally {
                    rs.close();
                }
                log.debug("UID identifier generated: " + result);
                return result;
            } finally {
                session.getBatcher().closeStatement(st);
            }
        } catch (SQLException sqle) {
            throw JDBCExceptionHelper.convert(
                    session.getFactory().getSQLExceptionConverter(),
                    sqle,
                    "could not retrieve UID",
                    sql
            );
        }
*/
    }

    public static String getUID() {
        //lifted from RandomValueVerifierServices.java
        byte[] verifierBytes = new byte[UID_LENGTH];
        (new Random()).nextBytes(verifierBytes);

        char[] chars = new char[verifierBytes.length];
        for (int i = 0; i < verifierBytes.length; i++) {
            chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
        }
        return new String(chars);
    }

    public static String getUID(int length) {
        //lifted from RandomValueVerifierServices.java
        byte[] verifierBytes = new byte[length];
        (new Random()).nextBytes(verifierBytes);

        char[] chars = new char[verifierBytes.length];
        for (int i = 0; i < verifierBytes.length; i++) {
            chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
        }
        return new String(chars);
    }
}