package com.cg.ams.service;

import com.cg.ams.entity.CourseEntity;

import com.cg.ams.exception.CourseNotFoundException;
import com.cg.ams.exception.DuplicateRecordException;
import com.cg.ams.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    ICourseRepository courseRepo;

    @Override
    public String add(CourseEntity course) {
        Optional<CourseEntity> opt = courseRepo.findById(course.getId());
        if (opt.isPresent()) {
            throw new DuplicateRecordException("Duplicate Record Entered with id->" + course.getId());
        } else {
            courseRepo.save(course);
            return "Course Added Successfully";
        }
    }

    @Override
    public CourseEntity update(CourseEntity course) {
    	Optional<CourseEntity> opt=courseRepo.findById(course.getId());
		if(!opt.isPresent()) {
			throw new CourseNotFoundException("Could not find the Course with id->"+course.getId());
		}
		else {
			courseRepo.save(course);
			return course;
		}


    }

    @Override
    public CourseEntity delete(CourseEntity course) {
    	Optional<CourseEntity> opt=courseRepo.findById(course.getId());
		if(!opt.isPresent()) {
			throw new CourseNotFoundException("Could not find the Course with id->"+course.getId());
		}
		else {
			courseRepo.delete(course);
			return course;
		}

    }


    @Override
    public CourseEntity findByName(String name) {
        CourseEntity course = courseRepo.findByName(name);
        if (course != null) {
            return course;
        } else {
            throw new CourseNotFoundException("Could not find the Course with name->" + name);
        }
    }

    @Override
    public CourseEntity findById(long id) {

        Optional<CourseEntity> opt = courseRepo.findById(id);
        if (!opt.isPresent()) {
            throw new CourseNotFoundException("Could not find the Course with id->" + id);
        } else {
            return opt.get();
        }
    }

    @Override
    public CourseEntity deleteById(long id) {
        Optional<CourseEntity> opt = courseRepo.findById(id);
        if (!opt.isPresent()) {
            throw new CourseNotFoundException("Could not find the Course with id->" + id);
        }
        courseRepo.deleteById(id);
        return opt.get();
    }

    @Override
    public CourseEntity deleteByName(String name) {
        CourseEntity course = courseRepo.findByName(name);
        if (course != null) {
            courseRepo.delete(course);
            return course;
        } else {
            throw new CourseNotFoundException("Could not find the Course with name->" + name);
        }
    }

    @Override
    public List<CourseEntity> getAllCourses() {
       return courseRepo.findAll();
   
    }

    @Override
    public CourseEntity updateNameById(long id, String name) {
        Optional<CourseEntity> opt = courseRepo.findById(id);
        if (!opt.isPresent()) {
            throw new CourseNotFoundException("Could not find the Course with id->" + id);
        }
        CourseEntity course = opt.get();
        course.setName(name);
        courseRepo.save(course);
        return course;

    }

}
