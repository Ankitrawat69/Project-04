package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.InsuranceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.InsuranceModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * InsuranceCtl handles add, update, view and navigation operations
 * for Insurance module.
 * 
 * @author Ankit Rawat
 * @version 1.0
 */
@WebServlet(name = "InsuranceCtl", urlPatterns = { "/ctl/InsuranceCtl" })
public class InsuranceCtl extends BaseCtl {

    /**
     * Validates Insurance form fields.
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("insuranceCode"))) {
            request.setAttribute("insuranceCode",
                    PropertyReader.getValue("error.require", "Insurance Code"));
            pass = false;
        }else if (!DataValidator.isInteger(request.getParameter("insuranceCode"))) {
            request.setAttribute("insuranceCode",
                    PropertyReader.getValue("error.require", "Insurance Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("carName"))) {
            request.setAttribute("carName",
                    PropertyReader.getValue("error.require", "Car Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("carName"))) {
            request.setAttribute("carName",
                    PropertyReader.getValue("error.require", "Car Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("providerName"))) {
            request.setAttribute("providerName",
                    PropertyReader.getValue("error.require", "Provider Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("providerName"))) {
            request.setAttribute("providerName",
                    PropertyReader.getValue("error.require", "Provider Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("expiryDate"))) {
            request.setAttribute("expiryDate",
                    PropertyReader.getValue("error.require", "Expiry Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("expiryDate"))) {
            request.setAttribute("expiryDate",
                    PropertyReader.getValue("error.date", "Expiry Date"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates InsuranceBean from request.
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        InsuranceBean bean = new InsuranceBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setInsuranceCode(DataUtility.getString(request.getParameter("insuranceCode")));
        bean.setCarName(DataUtility.getString(request.getParameter("carName")));
        bean.setProviderName(DataUtility.getString(request.getParameter("providerName")));
        bean.setExpiryDate(DataUtility.getDate(request.getParameter("expiryDate")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request (Edit/View).
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        InsuranceModel model = new InsuranceModel();

        if (id > 0) {
            try {
                InsuranceBean bean = model.findByPk(id);
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
     * Handles POST request.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        InsuranceModel model = new InsuranceModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            InsuranceBean bean = (InsuranceBean) populateBean(request);

            try {
                model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Insurance added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Insurance Code already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            InsuranceBean bean = (InsuranceBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Insurance updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Insurance Code already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.INSURANCE_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.INSURANCE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns Insurance JSP view.
     */
    @Override
    protected String getView() {
        return ORSView.INSURANCE_VIEW;
    }
}