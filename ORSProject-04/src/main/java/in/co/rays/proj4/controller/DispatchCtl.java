package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.DispatchBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.DispatchModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "DispatchCtl", urlPatterns = { "/ctl/DispatchCtl" })
public class DispatchCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("dispatchId"))) {
            request.setAttribute("dispatchId",
                    PropertyReader.getValue("error.require", "Dispatch ID"));
            pass = false;
        } else if (!DataValidator.isInteger(request.getParameter("dispatchId"))) {
            request.setAttribute("dispatchId", "Invalid dispatchId");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dispatchDate"))) {
            request.setAttribute("dispatchDate",
                    PropertyReader.getValue("error.require", "Dispatch Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("dispatchDate"))) {
            request.setAttribute("dispatchDate",
                    PropertyReader.getValue("error.date", "Dispatch Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("courierName"))) {
            request.setAttribute("courierName",
                    PropertyReader.getValue("error.require", "Courier Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("courierName"))) {
            request.setAttribute("courierName", "Invalid courierName");
            pass = false;
        }


        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        DispatchBean bean = new DispatchBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setDispatchId(DataUtility.getLong(request.getParameter("dispatchId")));
        bean.setDispatchDate(DataUtility.getDate(request.getParameter("dispatchDate")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));
        bean.setCourierName(DataUtility.getString(request.getParameter("courierName")));

        populateDTO(bean, request);

        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        DispatchModel model = new DispatchModel();

        if (id > 0) {
            try {

                DispatchBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        DispatchModel model = new DispatchModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            DispatchBean bean = (DispatchBean) populateBean(request);

            try {

                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Dispatch added successfully", request);

            } catch (DuplicateRecordException e) {

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Dispatch already exists", request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            DispatchBean bean = (DispatchBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Dispatch updated successfully", request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DISPATCH_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DISPATCH_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.DISPATCH_VIEW;
    }
}