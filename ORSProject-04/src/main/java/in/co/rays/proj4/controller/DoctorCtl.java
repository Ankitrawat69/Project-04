package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.DoctorBean;
import in.co.rays.proj4.bean.DropdownListBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.DoctorModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "DoctorCtl", urlPatterns = { "/ctl/DoctorCtl" })
public class DoctorCtl extends BaseCtl {

    /**
     * Preload Expertise dropdown
     */
	@Override
	protected void preload(HttpServletRequest request) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Cardiologist", "Cardiologist");
		map.put("Dentist", "Dentist");
		map.put("Pediatrician", "Pediatrician");
		map.put("Dermatologist","Dermatologist");
		map.put("Gynecologist","Gynecologist");
		request.setAttribute("map", map);
	}


    /**
     * Validation
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name",
                    PropertyReader.getValue("error.require", "Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.require", "Date of Birth"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo",
                    PropertyReader.getValue("error.require", "Mobile No"));
            pass = false;
        } else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo", "Invalid Mobile Number");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("expertise"))) {
            request.setAttribute("expertise",
                    PropertyReader.getValue("error.require", "Expertise"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populate Bean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        DoctorBean bean = new DoctorBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setExpertise(DataUtility.getString(request.getParameter("expertise")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * GET
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        DoctorModel model = new DoctorModel();

        if (id > 0) {
            try {
                DoctorBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * POST
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        DoctorModel model = new DoctorModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            DoctorBean bean = (DoctorBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Doctor added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Mobile Number already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            DoctorBean bean = (DoctorBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Doctor updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Mobile Number already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DOCTOR_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.DOCTOR_VIEW;
    }
}
