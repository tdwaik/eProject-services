package com.thaer.jj.controller.sellers;

import com.thaer.jj.entities.OfferVariation;
import com.thaer.jj.entities.OfferStockDetail;
import com.thaer.jj.entities.Size;
import com.thaer.jj.model.OfferModel;
import com.thaer.jj.model.SizeModel;
import com.thaer.jj.model.sets.OfferDetails;
import com.thaer.jj.model.sets.VariationDetails;
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
import java.util.Date;
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
            int brandId = Integer.parseInt(formDataMultiPart.getField("brand").getValue());
            String title = formDataMultiPart.getField("title").getValue();
            String description = formDataMultiPart.getField("description").getValue();

            offerDetails.offer.setCategoryId(categoryId);
            offerDetails.offer.setBrandId(brandId);
            offerDetails.offer.setTitle(title);
            offerDetails.offer.setDescription(description);

            offerDetails.offer.setBrandId(1);
            offerDetails.offer.setStatus("live");

            ArrayList<VariationDetails> variationsDetails = new ArrayList<>();
            VariationDetails variationDetail;
            OfferVariation offerVariation;

            ArrayList<OfferStockDetail> offerStockDetails;
            OfferStockDetail offerStockDetail;

            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setParseBigDecimal(true);

            int variationsCount = Integer.parseInt(formDataMultiPart.getField("variationsCount").getValue());
            for(int i = 0; i < variationsCount; i++) {
                offerVariation = new OfferVariation();
                offerStockDetails = new ArrayList<>();

                offerVariation.setStatus("live");

                String color = formDataMultiPart.getField("variations_" + i + "_color").getValue();
                offerVariation.setColor(color);

                SizeModel sizeModel = new SizeModel();
                ArrayList<Size> categorySizes = sizeModel.getSizesByCategoryId(categoryId);
                for(Size categorySize : categorySizes) {

                    FormDataBodyPart sizeFromData = formDataMultiPart.getField("variations_" + i + "_size_" + categorySize.getId());
                    if(sizeFromData != null && Boolean.parseBoolean(sizeFromData.getValue())) {
                        int stockQuantity = Integer.parseInt(formDataMultiPart.getField("variations_" + i + "_stockQuantity_" + categorySize.getId()).getValue());
                        BigDecimal price = (BigDecimal) decimalFormat.parse(formDataMultiPart.getField("variations_" + i + "_price_" + categorySize.getId()).getValue());
                        String sku = formDataMultiPart.getField("variations_" + i + "_sku_" + categorySize.getId()).getValue();

                        offerStockDetail = new OfferStockDetail();
                        offerStockDetail.setSizeId(categorySize.getId());
                        offerStockDetail.setStockQuantity(stockQuantity);
                        offerStockDetail.setPrice(price);
                        offerStockDetail.setSku(sku);
                        offerStockDetails.add(offerStockDetail);
                    }
                }

                Random random = new Random();
                Date date = new Date();
                String pictureFileName = random.nextInt(999999999) + "_" + date + ".jpg";

                offerVariation.setPicture(pictureFileName);

                List<FormDataBodyPart> mainPicture = formDataMultiPart.getFields("variations_" + i + "_mainPicture");
                for (FormDataBodyPart picture : mainPicture) {
                    uploadPicture(picture, "MC_" + pictureFileName);
                }

                int count = 1;
                List<FormDataBodyPart> pictures = formDataMultiPart.getFields("variations_" + i + "_pictures");
                for (FormDataBodyPart picture : pictures) {
                    uploadPicture(picture, (count++) + "_" + pictureFileName);
                }

                variationDetail = new VariationDetails();
                variationDetail.offerVariation = offerVariation;
                variationDetail.offerVariation.setTotalPictures(count);
                variationDetail.offerStockDetails = offerStockDetails;
                variationsDetails.add(variationDetail);

            }

            offerDetails.variationsDetails = variationsDetails;

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
        if(!mimeType.equals("image/png") && !mimeType.equals("image/jpeg")) {
            throw new IllegalArgumentException();
        }

        InputStream inputStream = picture.getValueAs(InputStream.class);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        if(bufferedImage == null) {
            throw new IllegalArgumentException();
        }

        int imageWidth = bufferedImage.getWidth();
        int imageHeight = bufferedImage.getHeight();

        int SWidth = 120;
        int MWidth = 300;
        int LWidth = 512;
        int XLWidth = (imageWidth > 900)? 900 : imageWidth;

        int SHeight = (SWidth * imageHeight) / imageWidth;
        int MHeight = (MWidth * imageHeight) / imageWidth;
        int LHeight = (LWidth * imageHeight) / imageWidth;
        int XLHeight = (XLWidth * imageHeight) / imageWidth;

        File file = new File("/home/stig/CDN/images/offers/O/" + pictureFileName);
        ImageIO.write(bufferedImage, "jpg", file);

        file = new File("/home/stig/CDN/images/offers/S/" + pictureFileName);
        ImageIO.write(resize(bufferedImage, SWidth, SHeight), "jpg", file);

        file = new File("/home/stig/CDN/images/offers/M/" + pictureFileName);
        ImageIO.write(resize(bufferedImage, MWidth, MHeight), "jpg", file);

        file = new File("/home/stig/CDN/images/offers/L/" + pictureFileName);
        ImageIO.write(resize(bufferedImage, LWidth, LHeight), "jpg", file);

        file = new File("/home/stig/CDN/images/offers/XL/" + pictureFileName);
        ImageIO.write(resize(bufferedImage, XLWidth, XLHeight), "jpg", file);

        return pictureFileName;

    }

    private BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
        return Thumbnails.of(img).size(newW, newH).asBufferedImage();
    }

}

