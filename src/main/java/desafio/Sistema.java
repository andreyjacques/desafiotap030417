package desafio;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helper.JsonHelper;
import model.Produto;

@WebServlet(urlPatterns = "/desafio")
public class Sistema extends HttpServlet {
	private List<Object> lista = new ArrayList<>();
	private JsonHelper jsonHelper = new JsonHelper();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nome = req.getParameter("descricao");
		float valor = Float.parseFloat(req.getParameter("valor"));
		int id = lista.size() + 1;
		Produto info = new Produto(id, nome, valor);
		lista.add(info);
		try {
			resp.getWriter().println(jsonHelper.gerarJson(info));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String json;
		if (req.getParameter("tipo").equals("som")) {
			float aux = 0;
			for (int i = 0; i < lista.size(); i++) {
				aux = aux + ((Produto) lista.get(i)).getValor();
			}
			resp.getWriter().println("{total: " + aux + "}");
		} else {
			try {
				json = jsonHelper.gerarJsonLista(lista);
				resp.getWriter().print(json);
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		for (int i = 0; i < lista.size(); i++) {
			if (Integer.parseInt(req.getParameter("id")) == ((Produto) lista.get(i)).getId())
				lista.remove(i);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nome = req.getParameter("descricao");
		float valor = Float.parseFloat(req.getParameter("valor"));
		int x = Integer.parseInt(req.getParameter("id"));
		Produto pro = (Produto) lista.get(x);
		for (int i = 0; i < lista.size(); i++) {
			if (Integer.parseInt(req.getParameter("id")) == ((Produto) lista.get(i)).getId()) {
				pro = (Produto) lista.get(i);
				pro.setDescricao(nome);
				pro.setValor(valor);
			}
		}
		try {
			resp.getWriter().println(jsonHelper.gerarJson(pro));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}
}
