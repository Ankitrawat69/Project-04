package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TrackingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.TrackingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TrackingCtl", urlPatterns = { "/ctl/TrackingCtl" })
public class TrackingCtl extends BaseCtl {

    /**
     * Preload Status dropdown
     */
    @Override
    protected void preload(HttpServletRequest request) {

        HashMap<String, String> map = new HashMap<>();

        map.put("Active", "Active");
        map.put("Inactive", "Inactive");

        request.setAttribute("statusList", map);
    }

    /**
     * Validation
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("trackingId"))) {

            request.setAttribute("trackingId",
                    PropertyReader.getValue("error.require", "Tracking ID"));

            pass = false;

        } else if (!DataValidator.isInteger(request.getParameter("trackingId"))) {

            request.setAttribute("trackingId", "Tracking ID must be numeric");

            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("trackingNumber"))) {

            request.setAttribute("trackingNumber",
                    PropertyReader.getValue("error.require", "Tracking Number"));

            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("currentLocation"))) {

            request.setAttribute("currentLocation",
                    PropertyReader.getValue("error.require", "Current Location"));

            pass = false;
        } else if (!DataValidator.isName(request.getParameter("currentLocation"))) {

            request.setAttribute("currentLocation", "currentLocation is invalid");

            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {

            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));

            pass = false;
        }

        return pass;
    }

    /**
     * Populate Bean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        TrackingBean bean = new TrackingBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setTrackingId(DataUtility.getLong(request.getParameter("trackingId")));
        bean.setTrackingNumber(DataUtility.getString(request.getParameter("trackingNumber")));
        bean.setCurrentLocation(DataUtility.getString(request.getParameter("currentLocation")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * GET Method
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        TrackingModel model = new TrackingModel();

        if (id > 0) {

            try {

                TrackingBean bean = model.findByPk(id);

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
     * POST Method
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        TrackingModel model = new TrackingModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            TrackingBean bean = (TrackingBean) populateBean(request);

            try {

                long pk = model.add(bean);

                ServletUtility.setBean(bean, request);

                ServletUtility.setSuccessMessage("Tracking added successfully", request);

            } catch (ApplicationException e) {

                e.printStackTrace();

                ServletUtility.handleException(e, request, response);

                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            TrackingBean bean = (TrackingBean) populateBean(request);

            try {

                if (id > 0) {

                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);

                ServletUtility.setSuccessMessage("Tracking updated successfully", request);

            } catch (ApplicationException e) {

                e.printStackTrace();

                ServletUtility.handleException(e, request, response);

                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.TRACKING_LIST_CTL, request, response);

            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.TRACKING_CTL, request, response);

            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * View
     */
    @Override
    protected String getView() {

        return ORSView.TRACKING_VIEW;

    }
}