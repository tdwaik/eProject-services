package com.thaer.jj.controller.sellers;

import com.thaer.jj.entities.OfferOption;
import com.thaer.jj.entities.OfferStockDetail;
import com.thaer.jj.entities.Size;
import com.thaer.jj.model.OfferModel;
import com.thaer.jj.model.SizeModel;
import com.thaer.jj.model.sets.OfferDetails;
import com.thaer.jj.model.sets.OfferOptionDetail;
import net.coobird.thumbnailator.Thumbnails;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Mar 31, 2016.
 */
@Path("sellers/offers")
public class OfferController extends SellersController {

    @PUT
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addOffer(FormDataMultiPart formDataMultiPart) {

        try {

            OfferDetails offerDetails = new OfferDetails();

            int categoryId = Integer.parseInt(formDataMultiPart.getField("category").getValue());
            String title = formDataMultiPart.getField("title").getValue();
            String description = formDataMultiPart.getField("description").getValue();

            offerDetails.offer.setCategoryId(categoryId);
            offerDetails.offer.setTitle(title);
            offerDetails.offer.setDescription(description);

            offerDetails.offer.setBrandId(1);
            offerDetails.offer.setStatus("live");

            ArrayList<OfferOptionDetail> offerOptionDetails = new ArrayList<>();
            OfferOptionDetail offerOptionDetail;
            OfferOption offerOption;

            ArrayList<OfferStockDetail> offerStockDetails;
            OfferStockDetail offerStockDetail;

            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setParseBigDecimal(true);

            int variationsCount = Integer.parseInt(formDataMultiPart.getField("variationsCount").getValue());
            for(int i = 0; i < variationsCount; i++) {
                offerOption = new OfferOption();
                offerStockDetails = new ArrayList<>();

                offerOption.setStatus("live");

                String color = formDataMultiPart.getField("variations_" + i + "_color").getValue();
                offerOption.setColor(color);

                SizeModel sizeModel = new SizeModel();
                ArrayList<Size> categorySizes = sizeModel.getSizesByCategoryId(categoryId);
                for(Size categorySize : categorySizes) {

                    FormDataBodyPart sizeFromData = formDataMultiPart.getField("variations_" + i + "_size_" + categorySize.getId());
                    if(sizeFromData != null && Boolean.parseBoolean(sizeFromData.getValue())) {
                        int stockQuantity = Integer.parseInt(formDataMultiPart.getField("variations_" + i + "_stockQuantity_" + categorySize.getId()).getValue());
                        BigDecimal price = (BigDecimal) decimalFormat.parse(formDataMultiPart.getField("variations_" + i + "_price_" + categorySize.getId()).getValue());

                        offerStockDetail = new OfferStockDetail();
                        offerStockDetail.setSizeId(categorySize.getId());
                        offerStockDetail.setStockQuantity(stockQuantity);
                        offerStockDetail.setPrice(price);
                        offerStockDetails.add(offerStockDetail);
                    }
                }

                Random random = new Random();
                String pictureFileName = random.nextInt() + "_" + random.nextInt() + "_" + random.nextInt() + ".jpg";

                offerOption.setPicture(pictureFileName);

                List<FormDataBodyPart> mainPicture = formDataMultiPart.getFields("variations_" + i + "_mainPicture");
                for (FormDataBodyPart picture : mainPicture) {
                    uploadPicture(picture, "M_" + pictureFileName);
                }

                List<FormDataBodyPart> pictures = formDataMultiPart.getFields("variations_" + i + "_pictures");
                int count = 1;
                for (FormDataBodyPart picture : pictures) {
                    uploadPicture(picture, (count++) + "_" + pictureFileName);
                }

                offerOptionDetail = new OfferOptionDetail();
                offerOptionDetail.offerOption = offerOption;
                offerOptionDetail.offerStockDetails = offerStockDetails;
                offerOptionDetails.add(offerOptionDetail);

            }

            offerDetails.offerOptionsDetails = offerOptionDetails;

            OfferModel offerModel = new OfferModel();
            offerModel.addOffers(getAuthUser(), offerDetails);

            return Response.status(Response.Status.CREATED).build();

        } catch (IOException | IllegalArgumentException | ParseException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }

    private String uploadPicture(FormDataBodyPart picture, String pictureFileName) throws IOException, IllegalArgumentException {

        String mimeType = picture.getMediaType().toString();
        if(!mimeType.equals("image/png") && !mimeType.equals("image/jpeg") && !mimeType.equals("image/gif")) {
            throw new IllegalArgumentException();
        }

        InputStream inputStream = picture.getValueAs(InputStream.class);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        if(bufferedImage == null) {
            throw new IllegalArgumentException();
        }

        int imageWidth = bufferedImage.getWidth();
        int imageHeight = bufferedImage.getHeight();

        int SHeight = (120 * imageHeight) / imageWidth;
        int MHeight = (300 * imageHeight) / imageWidth;
        int LHeight = (512 * imageHeight) / imageWidth;

        File file = new File("/home/stig/CDN/images/O_" + pictureFileName);
        ImageIO.write(bufferedImage, "jpg", file);

        file = new File("/home/stig/CDN/images/S_" + pictureFileName);
        ImageIO.write(resize(bufferedImage, 120, SHeight), "jpg", file);

        file = new File("/home/stig/CDN/images/M_" + pictureFileName);
        ImageIO.write(resize(bufferedImage, 320, MHeight), "jpg", file);

        file = new File("/home/stig/CDN/images/L_" + pictureFileName);
        ImageIO.write(resize(bufferedImage, 512, LHeight), "jpg", file);

        file = new File("/home/stig/CDN/images/XL_" + pictureFileName);
        ImageIO.write(resize(bufferedImage, 512, LHeight), "jpg", file);

        return pictureFileName;

    }

    private BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
        return Thumbnails.of(img).size(newW, newH).asBufferedImage();
    }

}

