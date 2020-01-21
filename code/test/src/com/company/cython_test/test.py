from PIL import Image
import numpy as np
import imageio
import math
import time


def read_image_return_nparray(fname):
    """
    open image use Image and convert it into np.array
    :param fname: image file name
    :return: np.array(image)
    """
    image = Image.open(fname)
    image_array = np.array(image)
    return image_array


def get_image_height_and_width_and_channels(image_array):
    return image_array.shape[1], image_array.shape[0], image_array.shape[2]


def barrel_correction2(src_image, param_a, param_b, param_c, param_d):
    xDim, yDim, zDim = get_image_height_and_width_and_channels(src_image)

    dest_image = np.zeros_like(src_image)

    xcen = (xDim - 1.0) / 2.0
    ycen = (yDim - 1.0) / 2.0
    normDist = min(xcen, ycen)

    imageMin = np.min(src_image)
    dest_image.fill(imageMin)

    for k in range(zDim):
        for j in range(yDim):
            yoff = (j - ycen) / normDist

            for i in range(xDim):
                xoff = (i - xcen) / normDist
                rdest2 = xoff * xoff + yoff * yoff
                rdest = math.sqrt(rdest2)
                rdest3 = rdest2 * rdest
                rdest4 = rdest2 * rdest2
                rsrc = param_a * rdest4 + param_b * rdest3 + param_c * rdest2 + param_d * rdest
                rsrc = normDist * rsrc
                ang = math.atan2(yoff, xoff)
                xSrc = xcen + (rsrc * math.cos(ang))
                ySrc = ycen + (rsrc * math.sin(ang))

                if 0 <= xSrc < xDim - 1 and 0 <= ySrc < yDim - 1:
                    xBase = int(math.floor(xSrc))
                    delX = float(xSrc - xBase)
                    yBase = int(math.floor(ySrc))
                    delY = float(ySrc - yBase)

                    dest_image[j][i][k] = int((1 - delX) * (1 - delY) * src_image[yBase][xBase][k])

                    if xSrc < (xDim - 1):
                        dest_image[j][i][k] += int(delX * (1 - delY) * src_image[yBase][xBase + 1][k])

                    if ySrc < (yDim - 1):
                        dest_image[j][i][k] += int((1 - delX) * delY * src_image[yBase + 1][xBase][k])

                    if (xSrc < (xDim - 1)) and (ySrc < (yDim - 1)):
                        dest_image[j][i][k] += int(delX * delY * src_image[yBase + 1][xBase + 1][k])

    return dest_image


def save_image_array(image_array, fname):
    imageio.imwrite(fname, image_array)


def main():
    original_image = read_image_return_nparray(
        "/Users/pengyuyan/Desktop/singleFrame/images/singleImages/3.png")

    paramA = 0.08  # affects only the outermost pixels of the image
    paramB = -0.36  # most cases only require b optimization
    paramC = 0.0  # most uniform correction
    paramD = 1.0 - paramA - paramB - paramC  # describes the linear scaling of the image

    corrected_original_image = barrel_correction2(original_image, paramA, paramB, paramC, paramD)
    save_image_array(corrected_original_image,
                     "/Users/pengyuyan/Desktop/singleFrame/images/mytest_result_images/barrel3_result_python.png")


if __name__ == "__main__":
    start = time.time()
    print("start!!! " + time.ctime())
    main()
    end = time.time()
    print("end!!! " + time.ctime())
    print("duration: {}".format(end - start))
