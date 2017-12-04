package com.showtime.xijing.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * xml 与 Java Bean 的转换工具
 *
 * @author liudajiang
 * @create 2017-11-23
 */
@Slf4j
public class XMLTransform {
    /**
     * 对象转换为XML
     *
     * @param object 要转换的对象,支持集合
     * @return xml字符串
     */
    public static String toXml(Object object) {
        try {
            List<Class> bindClassList = new ArrayList<>();
            if (object instanceof Collection) {
                Collection collection = (Collection) object;
                bindClassList.add(CollectionWrapper.class);
                Iterator collectionIterator = collection.iterator();
                if (collectionIterator.hasNext()) {
                    bindClassList.add(collectionIterator.next().getClass());
                }
                QName qName = new QName("items");
                CollectionWrapper wrapper = new CollectionWrapper(collection);//
                JAXBElement<CollectionWrapper> jaxbElement = new JAXBElement<>(qName, CollectionWrapper.class, wrapper);
                object = jaxbElement;
            } else {
                bindClassList.add(object.getClass());
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(bindClassList.toArray(new Class[]{}));
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            jaxbMarshaller.marshal(object, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * XML转换为对象列表
     *
     * @param xml       xml 字符串
     * @param itemClazz 集合元素的class
     * @return
     */
    public static <T> List<T> toBeanList(String xml, Class<T> itemClazz) {
        try {
            InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8.name()));

            JAXBContext jaxbContext = JAXBContext.newInstance(CollectionWrapper.class, itemClazz);
            //反序列化
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (List) unmarshaller.unmarshal(new StreamSource(stream), CollectionWrapper.class).getValue().getItems();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * XML转换为对象
     *
     * @param xml   xml 字符串
     * @param clazz 实体类class
     * @return
     */
    public static <T> T toBean(String xml, Class<T> clazz) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            //反序列化
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    
    static class CollectionWrapper<T> {
        CollectionWrapper() {

        }

        CollectionWrapper(Collection<T> items) {
            this.items = items;
        }

        private Collection<T> items = new ArrayList<>(0);

        @XmlAnyElement(lax = true)
        public Collection<T> getItems() {
            return items;
        }
    }
}
