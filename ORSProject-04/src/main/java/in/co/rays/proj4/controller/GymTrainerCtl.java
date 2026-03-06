package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.GymTrainerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.GymTrainerModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * GymTrainerCtl handles trainer related operations such as
 * add, update, view and navigation.
 * 
 * Supported operations:
 * Save, Update, Cancel, Reset
 * 
 * @author Your Name
 * @version 1.0
 */
@WebServlet(name = "GymTrainerCtl", urlPatterns = { "/ctl/GymTrainerCtl" })
public class GymTrainerCtl extends BaseCtl {
	
	@Override
	protected void preload(HttpServletRequest request) {

	    Map<String, String> map = new LinkedHashMap<>();

	    map.put("Cardio", "Cardio");
	    map.put("Yoga", "Yoga");
	    map.put("Zumba", "Zumba");
	    map.put("Weight Training", "Weight Training");
	    map.put("CrossFit", "CrossFit");
	    map.put("Personal Training", "Personal Training");

	    request.setAttribute("specializationMap", map);
	}

    /**
     * Validates trainer form fields.
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("trainerName"))) {
            request.setAttribute("trainerName",
                    PropertyReader.getValue("error.require", "Trainer Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("trainerName"))) {
            request.setAttribute("trainerName", "invalid Trainer Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("specialization"))) {
            request.setAttribute("specialization",
                    PropertyReader.getValue("error.require", "Specialization"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("specialization"))) {
            request.setAttribute("specialization", "Invalid specialization");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("salary"))) {
            request.setAttribute("salary",
                    PropertyReader.getValue("error.require", "Salary"));
            pass = false;
        } else if (!DataValidator.isInteger(request.getParameter("salary"))) {
            request.setAttribute("salary", "Salary must be a number");
            pass = false;
        }

        return pass;
    }

    /**
     * Populates GymTrainerBean from request.
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        GymTrainerBean bean = new GymTrainerBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setTrainerName(DataUtility.getString(request.getParameter("trainerName")));
        bean.setSpecialization(DataUtility.getString(request.getParameter("specialization")));
        bean.setSalary(DataUtility.getString(request.getParameter("salary")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request (for edit/view).
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        GymTrainerModel model = new GymTrainerModel();

        if (id > 0) {
            try {
                GymTrainerBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles POST request for Save/Update/Cancel/Reset.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        GymTrainerModel model = new GymTrainerModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            GymTrainerBean bean = (GymTrainerBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Trainer added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Trainer already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            GymTrainerBean bean = (GymTrainerBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Trainer updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Trainer already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.GYMTRAINER_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.GYMTRAINER_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns view page.
     */
    @Override
    protected String getView() {
        return ORSView.GYMTRAINER_VIEW;
    }
}