package com.catt.common.util.excel;

import org.apache.poi.ss.util.CellReference;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel工具类
 * 
 * @author 纪建宏
 * 
 */
public class ExcelUtils {

	/**
	 * 转换ResultSet对象为List数组
	 * 
	 * @param resultSet
	 * @param containTitle
	 *            是否包含列标题
	 * @return
	 * @throws SQLException
	 */
	public static List<List<Object>> getListListByResultSet(
			ResultSet resultSet, boolean containTitle) throws SQLException {
		List<List<Object>> result = new ArrayList<List<Object>>();

		if (resultSet != null) {
			List<Object> fields = new ArrayList<Object>();
			// 得到结果集的结构信息，比如字段数、字段名等
			ResultSetMetaData metaData = resultSet.getMetaData();
			// 获得此 ResultSet 对象中的列数
			int columnCount = metaData.getColumnCount();

			// 注意rs.get的下标是1开始的。
			for (int i = 1; i <= columnCount; i++) {
				fields.add(metaData.getColumnName(i));
			}

			if (containTitle) {
				result.add(fields);
			}

			while (resultSet.next()) {
				List<Object> rowData = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++) {
					rowData.add(resultSet.getObject(i));
				}
				result.add(rowData);
			}
		}

		return result;
	}

	/**
	 * 转换Map数组为List数组
	 * 
	 * @param listMap
	 * @param containTitle
	 *            是否包含列标题
	 * @return
	 */
	public static List<List<Object>> getListListByListMap(
			List<Map<Object, Object>> listMap, boolean containTitle) {
		List<Object> keyOrder = null;

		if (listMap != null && listMap.size() > 0 && listMap.get(0) != null) {
			Map<Object, Object> map = listMap.get(0);
			keyOrder = new ArrayList<Object>();
			keyOrder.addAll(map.keySet());
		}

		return getListListByListMap(listMap, keyOrder, containTitle);
	}

	/**
	 * 转换Map数组为List数组
	 * 
	 * @param listMap
	 * @param keyOrder
	 *            key顺序
	 * @param containTitle
	 *            是否包含列标题
	 * @return
	 */
	public static List<List<Object>> getListListByListMap(
			List<Map<Object, Object>> listMap, List<Object> keyOrder,
			boolean containTitle) {
		List<List<Object>> result = new ArrayList<List<Object>>();

		int keyOrderSize = keyOrder.size();
		if (keyOrder != null && keyOrderSize > 0) {
			if (containTitle) {
				result.add(keyOrder);
			}

			if (listMap != null) {
				int listMapSize = listMap.size();
				for (int i = 0; i < listMapSize; i++) {
					List<Object> row = new ArrayList<Object>();
					Map<Object, Object> map = listMap.get(i);

					if (map != null) {
						for (int j = 0; j < keyOrderSize; j++) {
							row.add(map.get(keyOrder.get(j)));
						}
					}
					result.add(row);
				}
			}
		}

		return result;
	}

	/**
	 * 转换字符串为List数组，以“\n”为行分隔符，“,”为列分隔符
	 * 
	 * @param content
	 * @return
	 */
	public static List<List<Object>> getListListByString(String content) {
		return getListListByString(content, "\n", ",");
	}

	/**
	 * 转换字符串为List数组
	 * 
	 * @param content
	 * @param rowDelimiter
	 *            行分隔符
	 * @param colDelimiter
	 *            列分隔符
	 * @return
	 */
	public static List<List<Object>> getListListByString(String content,
			String rowDelimiter, String colDelimiter) {
		List<List<Object>> result = new ArrayList<List<Object>>();

		if (content != null && !"".equals(content)) {
			String[] rows = content.split(rowDelimiter, -1);

			for (int i = 0; i < rows.length; i++) {
				String row = rows[i];

				List<Object> rowData = new ArrayList<Object>();
				if (row != null && !"".equals(row)) {
					String[] cells = row.split(colDelimiter, -1);

					for (int j = 0; j < cells.length; j++) {
						rowData.add(cells[j]);
					}
				}

				result.add(rowData);
			}
		}

		return result;
	}

	/**
	 * 转换List数组为Map数组
	 * 
	 * @param listList
	 * @return
	 */
	public static List<Map<Object, Object>> getListMapByListList(
			List<List<Object>> listList) {
		return getListMapByListList(listList,false);
	}


	/**
	 * 转换List数组为Map数组
	 *
	 * @param listList
	 * @param isIgnoreEmptyRow 是否忽略空行
	 * @return
	 */
	public static List<Map<Object, Object>> getListMapByListList(
			List<List<Object>> listList,boolean isIgnoreEmptyRow) {
		List<Map<Object, Object>> result = new ArrayList<Map<Object, Object>>();

		int listListSize = listList.size();
		if (listList != null && listListSize > 1) {
			List<Object> keys = listList.get(0);

			for (int i = 1; i < listListSize; i++) {
				Map<Object, Object> map = new HashMap<Object, Object>();
				boolean isHasEmptyRow = true;

				if (keys != null) {
					List<Object> list = listList.get(i);
					int keySize = keys.size();

					for (int j = 0; j < keySize; j++) {
                        // 排除空列
                        if(null == keys.get(j)){
                            continue;
                        }
						if (j < list.size()) {
							if(list.get(j) != null && com.catt.common.util.lang.StringUtil.isNotBlank(list.get(j).toString())){
								isHasEmptyRow = false;
							}
							map.put(keys.get(j), list.get(j));
						} else {
							map.put(keys.get(j), null);
						}
					}
				}

				if(!isIgnoreEmptyRow || (isIgnoreEmptyRow && !isHasEmptyRow)){
					result.add(map);
				}
			}
		}

		return result;
	}


	/**
	 * 转换字符串为Map数组
	 * 
	 * @param content
	 * @return
	 */
	public static List<Map<Object, Object>> getListMapByString(String content) {
		return getListMapByListList(getListListByString(content));
	}

	/**
	 * 转换字符串为Map数组
	 * 
	 * @param content
	 * @param rowDelimiter
	 *            行分隔符
	 * @param colDelimiter
	 *            列分隔符
	 * @return
	 */
	public static List<Map<Object, Object>> getListMapByString(String content,
			String rowDelimiter, String colDelimiter) {
		return getListMapByListList(getListListByString(content, rowDelimiter,
				colDelimiter));
	}

	/**
	 * 转换List数组为字符串<br>
	 * 以“\n”为行分隔符，“,”为列分隔符，“yyyy-MM-dd HH:mm:ss”为日期格式
	 * 
	 * @param listList
	 * @return
	 */
	public static String getStringByListList(List<List<Object>> listList) {
		return getStringByListList(listList, "\n", ",", "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 转换List数组为字符串
	 * 
	 * @param listList
	 * @param rowDelimiter
	 *            行分隔符
	 * @param colDelimiter
	 *            列分隔符
	 * @param dateFormat
	 *            日期格式
	 * @return
	 */
	public static String getStringByListList(List<List<Object>> listList,
			String rowDelimiter, String colDelimiter, String dateFormat) {
		StringBuilder result = new StringBuilder();

		if (listList != null) {
			int listListSize = listList.size();
			for (int i = 0; i < listListSize; i++) {
				List<Object> row = listList.get(i);

				if (row != null) {
					int rowSize = row.size();
					for (int j = 0; j < rowSize; j++) {
						Object cell = row.get(j);
						result.append(objectToString(cell, dateFormat));

						if (j < rowSize - 1) {
							result.append(colDelimiter);
						}
					}
				}

				if (i < listListSize - 1) {
					result.append(rowDelimiter);
				}
			}
		}

		return result.toString();
	}

	/**
	 * Object对象转字符串
	 * 
	 * @param obj
	 *            Object对象
	 * @param dateFormat
	 *            日期格式
	 * @return
	 */
	private static String objectToString(Object obj, String dateFormat) {
		if (obj == null) {
			return "";
		}

		if (obj instanceof Date) {
			return new SimpleDateFormat(dateFormat).format((Date) obj);
		}

		return obj.toString();
	}

	/**
	 * 转换Map数组为字符串
	 * 
	 * @param listMap
	 * @param containTitle
	 *            是否包含列标题
	 * @return
	 */
	public static String getStringByListMap(List<Map<Object, Object>> listMap,
			boolean containTitle) {
		return getStringByListList(getListListByListMap(listMap, containTitle));
	}

	/**
	 * 转换Map数组为字符串
	 * 
	 * @param listMap
	 * @param rowDelimiter
	 *            行分隔符
	 * @param colDelimiter
	 *            列分隔符
	 * @param dateFormat
	 *            日期格式
	 * @param containTitle
	 *            是否包含列标题
	 * @return
	 */
	public static String getStringByListMap(List<Map<Object, Object>> listMap,
			String rowDelimiter, String colDelimiter, String dateFormat,
			boolean containTitle) {
		return getStringByListList(getListListByListMap(listMap, containTitle),
				rowDelimiter, colDelimiter, dateFormat);
	}

	/**
	 * 转换列值为列号
	 * 
	 * @param col
	 *            列值
	 * @return
	 */
	public static int convertColStringToIndex(String col) {
		return CellReference.convertColStringToIndex(col);
	}

	/**
	 * 转换列号为列值
	 * 
	 * @param colIndex
	 *            列号
	 * @return
	 */
	public static String convertColIndexToString(int colIndex) {
		return CellReference.convertNumToColString(colIndex);
	}
}
