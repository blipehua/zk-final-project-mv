package pe.edu.nh.binding;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;



import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import com.google.gson.Gson;

import pe.edu.nh.dto.FormularioDTO;
import pe.edu.nh.model.*;
import pe.edu.nh.rest.RestCliente;

public class DynamicFormViewModel {
	
	private List<FormularioDTO> campos;
	private RestCliente restCliente;
	private int idFormulario;
	
	@Init
	public void init() throws MalformedURLException, IOException {
		restCliente = new RestCliente();
		campos = restCliente.getFormulario(3);		
	}
	
	public void setIdFormulario(int idFormulario) {
		this.idFormulario = idFormulario;
	}
	
	public int getIdFormulario() {
		return this.idFormulario;
	}
	
	public List<FormularioDTO> getCampos(){
		return campos;
	}
	
	public void setCampos(List<FormularioDTO> campos) {
		this.campos	= campos;
	}
	
	@Command
	public void submitForm() {
		RestCliente restCliente = new RestCliente();
		Gson gson = new Gson();
		
		campos.forEach(form -> {
			try {
				
				FormularioDTO responseDto = restCliente.grabarFormulario(form);
				String jsonOutputString = gson.toJson(responseDto);
				System.out.println(jsonOutputString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}
	
	@Command
	@NotifyChange("campos")
	public void selFormulario(@BindingParam("idFormulario") int idFormulario) throws MalformedURLException, IOException {
		restCliente = new RestCliente();
		campos = restCliente.getFormulario(idFormulario);
		campos.forEach(c -> c.setId(idFormulario));
	}
	
	
	@Command
	@NotifyChange("campos")
	public void clearFormulario() {
		campos.forEach(t -> t.setValor(null));
	}
	

}
