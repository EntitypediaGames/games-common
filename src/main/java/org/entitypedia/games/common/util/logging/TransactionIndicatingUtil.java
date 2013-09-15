package org.entitypedia.games.common.util.logging;

/**
 * From http://java.dzone.com/articles/monitoring-declarative-transac?page=0,1
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class TransactionIndicatingUtil {
    private final static String TSM_CLASSNAME = "org.springframework.transaction.support.TransactionSynchronizationManager";

    @SuppressWarnings("unchecked")
    public static String getTransactionStatus(boolean verbose) {
        String status;

        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null) {
                Class tsmClass = contextClassLoader.loadClass(TSM_CLASSNAME);
                Boolean isActive = (Boolean) tsmClass.getMethod("isActualTransactionActive").invoke(null);
                if (!verbose) {
                    status = (isActive) ? "[+] " : "[-] ";
                } else {
                    String transactionName = (String) tsmClass.getMethod("getCurrentTransactionName").invoke(null);
                    status = (isActive) ? "[" + transactionName + "] " : "[no transaction] ";
                }
            } else {
                status = (verbose) ? "[ccl unavailable] " : "[x ]";
            }
        } catch (Exception e) {
            status = (verbose) ? "[spring unavailable] " : "[x ]";
        }
        return status;
    }
}