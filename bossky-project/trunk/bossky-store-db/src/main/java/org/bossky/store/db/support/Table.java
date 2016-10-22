package org.bossky.store.db.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表
 * 
 * @author bo
 *
 */
public class Table {
	protected String catalog;
	protected String schema;
	protected String name;
	protected List<Column> columns;

	private Table() {

	}

	public static Table valueOf(ResultSet rs) throws SQLException {
		String catalog;
		String schema;
		String name;
		List<Column> columns = new ArrayList<Column>();
		if (rs.next()) {// 先判断有没有数据
			// TABLE_CAT
			// TABLE_SCHEM
			// TABLE_NAME
			catalog = rs.getString("TABLE_CAT");
			schema = rs.getString("TABLE_SCHEM");
			name = rs.getString("TABLE_NAME");
			columns.add(new Column(rs));
		} else {
			// throw new NullPointerException("数据库表不存在");
			return null;
		}
		while (rs.next()) {
			columns.add(new Column(rs));
		}
		Table table = new Table();
		table.catalog = catalog;
		table.schema = schema;
		table.name = name;
		table.columns = columns;
		return table;
	}

	/**
	 * 表名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getSchema() {
		return schema;
	}

	public String toString() {
		return name + "(" + columns + ");";
	}

	public static class Column {

		protected String name;
		protected String type;

		private Column(ResultSet rs) throws SQLException {
			name = rs.getString("COLUMN_NAME");
			type = rs.getString("TYPE_NAME");
		}

		/**
		 * 名称
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}

		/**
		 * 类型
		 * 
		 * @return
		 */
		public String getType() {
			return type;
		}

		@Override
		public String toString() {
			return name + "=" + type;
		}
	}
	// TABLE_CAT
	// TABLE_SCHEM
	// TABLE_NAME
	// COLUMN_NAME
	// DATA_TYPE
	// TYPE_NAME
	// COLUMN_SIZE
	// BUFFER_LENGTH
	// DECIMAL_DIGITS
	// NUM_PREC_RADIX
	// NULLABLE
	// REMARKS
	// COLUMN_DEF
	// SQL_DATA_TYPE
	// SQL_DATETIME_SUB
	// CHAR_OCTET_LENGTH
	// ORDINAL_POSITION
	// IS_NULLABLE
	// SCOPE_CATLOG
	// SCOPE_SCHEMA
	// SCOPE_TABLE
}
