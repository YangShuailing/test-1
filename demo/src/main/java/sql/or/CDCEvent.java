package sql.or;

import com.google.code.or.binlog.BinlogEventV4;
import com.google.code.or.binlog.BinlogEventV4Header;
import com.google.code.or.binlog.impl.event.AbstractBinlogEventV4;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Author: Johnny
 * Date: 2017/7/1
 * Time: 22:17
 */
public class CDCEvent {
    private long eventId = 0;//事件唯一标识
    private String databaseName = null;
    private String tableName = null;
    private int eventType = 0;//事件类型
    private long timestamp = 0;//事件发生的时间戳[MySQL服务器的时间]
    private long timestampReceipt = 0;//Open-replicator接收到的时间戳[CDC执行的时间戳]
    private String binlogName = null;// binlog file name
    private long position = 0;
    private long nextPostion = 0;
    private long serverId = 0;
    private Map<String, String> before = null;
    private Map<String, String> after = null;
    private Boolean isDdl = null;
    private String sql = null;

    private static AtomicLong uuid = new AtomicLong(0);

    public CDCEvent() {
    }

    CDCEvent(final AbstractBinlogEventV4 are, String databaseName, String tableName) {
        this.init(are);
        this.databaseName = databaseName;
        this.tableName = tableName;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestampReceipt() {
        return timestampReceipt;
    }

    public void setTimestampReceipt(long timestampReceipt) {
        this.timestampReceipt = timestampReceipt;
    }

    public String getBinlogName() {
        return binlogName;
    }

    public void setBinlogName(String binlogName) {
        this.binlogName = binlogName;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getNextPostion() {
        return nextPostion;
    }

    public void setNextPostion(long nextPostion) {
        this.nextPostion = nextPostion;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public Map<String, String> getBefore() {
        return before;
    }

    public void setBefore(Map<String, String> before) {
        this.before = before;
    }

    public Map<String, String> getAfter() {
        return after;
    }

    public void setAfter(Map<String, String> after) {
        this.after = after;
    }

    public Boolean getDdl() {
        return isDdl;
    }

    void setIsDdl(Boolean ddl) {
        isDdl = ddl;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public static AtomicLong getUuid() {
        return uuid;
    }

    public static void setUuid(AtomicLong uuid) {
        CDCEvent.uuid = uuid;
    }

    private void init(final BinlogEventV4 be) {
        this.eventId = uuid.getAndAdd(1);
        BinlogEventV4Header header = be.getHeader();

        this.timestamp = header.getTimestamp();
        this.eventType = header.getEventType();

        this.serverId = header.getServerId();
        this.timestampReceipt = header.getTimestampOfReceipt();
        this.position = header.getPosition();
        this.nextPostion = header.getNextPosition();
//        this.binlogName = header.getBinlogFileName();
    }

    @Override
    public String toString() {
        return "{ eventId:" + eventId +
                ",databaseName:" + databaseName +
                ",tableName:" + tableName +
                ",eventType:" + eventType +
                ",timestamp:" + timestamp +
                ",timestampReceipt:" + timestampReceipt +
                ",binlogName:" + binlogName +
                ",position:" + position +
                ",nextPostion:" + nextPostion +
                ",serverId:" + serverId +
                ",isDdl:" + isDdl +
                ",sql:" + sql +
                ",before:" + before +
                ",after:" + after + "}";
    }
}
