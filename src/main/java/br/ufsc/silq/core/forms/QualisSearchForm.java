package br.ufsc.silq.core.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualisSearchForm {

	private String query = "";

	private String area;

	public QualisSearchForm(String query) {
		this.query = query;
	}

	public boolean hasQuery() {
		return StringUtils.isNotBlank(this.query);
	}

	public boolean hasArea() {
		return StringUtils.isNotBlank(this.area);
	}
}
